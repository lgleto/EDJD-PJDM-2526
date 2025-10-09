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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.example.lastnews.ui.articles.ArticleDetailView
import ipca.example.lastnews.ui.articles.ArticlesListView
import ipca.example.lastnews.ui.theme.LastNewsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var topBarTitle by remember{mutableStateOf("")}
            var isHomeScreen by remember { mutableStateOf(true) }
            LastNewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text(topBarTitle) },
                            actions = {
                                IconButton(onClick = {}) {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = "Search"
                                    )
                                }
                            },
                            navigationIcon = {

                                if(!isHomeScreen) {
                                    IconButton(onClick = {
                                        navController.popBackStack()
                                    }) {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowBack,
                                            contentDescription = "Search"
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "articles",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("articles") {
                            topBarTitle = "Crunch Tech"
                            isHomeScreen = true
                            ArticlesListView(
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
