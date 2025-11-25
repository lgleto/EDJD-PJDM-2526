package ipca.example.lastnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.example.lastnews.models.AppDatabase
import ipca.example.lastnews.models.Article
import ipca.example.lastnews.ui.articles.ArticleDetailView
import ipca.example.lastnews.ui.articles.ArticlesFavoritesListView
import ipca.example.lastnews.ui.articles.ArticlesListView
import ipca.example.lastnews.ui.components.MyBottomBar
import ipca.example.lastnews.ui.components.MyTopBar
import ipca.example.lastnews.ui.theme.LastNewsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var topBarTitle by remember{mutableStateOf("")}
            var isHomeScreen by remember { mutableStateOf(true) }
            var clickedArticle by remember { mutableStateOf<Article?>(null) }
            val context = LocalContext.current
            var scope = rememberCoroutineScope()
            LastNewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        MyTopBar(
                            topBarTitle = topBarTitle,
                            isHomeScreen = isHomeScreen,
                            navController = navController,
                            onClickFavorite = {
                                scope.launch (Dispatchers.IO) {
                                    AppDatabase
                                        .getInstance(context)
                                        ?.articleDao()
                                        ?.insert(clickedArticle!!)
                                }

                            }
                        )
                    },
                    bottomBar = {
                        MyBottomBar(
                            navController = navController
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "bloomberg",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("bloomberg") {
                            topBarTitle = "Bloomberg"
                            isHomeScreen = true
                            ArticlesListView(
                                navController = navController,
                                source = "bloomberg",
                                onArticleClick = { clickedArticle = it }
                            )
                        }
                        composable("espn") {
                            topBarTitle = "ESPN"
                            isHomeScreen = true
                            ArticlesListView(
                                navController = navController,
                                source = "espn",
                                onArticleClick = { clickedArticle = it }
                            )
                        }
                        composable("techcrunch") {
                            topBarTitle = "Crunch Tech"
                            isHomeScreen = true
                            ArticlesListView(
                                navController = navController,
                                source = "techcrunch",
                                onArticleClick = { clickedArticle = it }
                            )
                        }
                        composable ("favorites"){
                            topBarTitle = "Favorites"
                            isHomeScreen = true
                            ArticlesFavoritesListView(
                                navController = navController
                            )
                        }
                        composable("articles_detail/{articleUrl}") {
                            topBarTitle = "Article"
                            isHomeScreen = false
                            val articleUrl = it.arguments?.getString("articleUrl")?:""
                            ArticleDetailView(articleUrl)
                        }
                    }
                }
            }
        }
    }
}
