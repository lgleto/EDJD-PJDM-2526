package ipca.example.lastnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ipca.example.lastnews.ui.articles.ArticleDetailView
import ipca.example.lastnews.ui.articles.ArticlesListView
import ipca.example.lastnews.ui.theme.LastNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            LastNewsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "articles",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("articles") {
                            ArticlesListView(
                                navController = navController
                            )
                        }
                        composable("articles_detail/{articleUrl}") {
                            val articleUrl = it.arguments?.getString("articleUrl")?:""
                            ArticleDetailView(articleUrl)
                        }
                    }
                }
            }
        }
    }
}
