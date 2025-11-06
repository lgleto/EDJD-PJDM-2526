package ipca.example.gametips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import ipca.example.gametips.ui.tips.TipsView
import ipca.example.gametips.ui.gamestips.AddGameView
import ipca.example.gametips.ui.gamestips.GameTipsView
import ipca.example.gametips.ui.login.LoginView
import ipca.example.gametips.ui.profile.ProfileView
import ipca.example.gametips.ui.theme.GameTipsTheme
import ipca.example.lastnews.ui.components.MyBottomBar
import ipca.example.lastnews.ui.components.MyTopBar

const val TAG = "GameTips"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var title by remember { mutableStateOf("Game Tips") }
            var isHome by remember { mutableStateOf(true) }
            GameTipsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        MyBottomBar(
                            navController = navController
                        )
                    },
                    topBar = {
                        MyTopBar(
                            topBarTitle = title,
                            isHomeScreen = isHome,
                            navController
                        )
                    }
                    ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("login"){
                            LoginView(navController = navController)
                        }
                        composable("home"){
                            isHome = true
                            GameTipsView(
                                navController = navController
                            )
                        }
                        composable ("add_game"){
                            isHome = false
                            AddGameView(
                                navController = navController
                            )
                        }
                        composable ("tips/{gameId}"){
                            isHome = false
                            val gameId = it.arguments?.getString("gameId")?:""
                            TipsView(
                                navController = navController,
                                gameId = gameId
                            )
                        }
                        composable ("profile"){
                            isHome = true
                            ProfileView()
                        }
                    }
                }
            }
            LaunchedEffect(Unit) {
                val currentUser  = Firebase.auth.currentUser
                if (currentUser != null) {
                    navController.navigate("home")
                }
            }
        }
    }
}
