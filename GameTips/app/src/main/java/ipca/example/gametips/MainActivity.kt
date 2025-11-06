package ipca.example.gametips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
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
import ipca.example.gametips.ui.theme.GameTipsTheme

const val TAG = "GameTips"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            GameTipsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("login"){
                            LoginView(navController = navController)
                        }
                        composable("home"){
                            GameTipsView(
                                navController = navController
                            )
                        }
                        composable ("add_game"){
                            AddGameView(
                                navController = navController
                            )
                        }
                        composable ("tips/{gameId}"){
                            val gameId = it.arguments?.getString("gameId")?:""
                            TipsView(
                                navController = navController,
                                gameId = gameId
                            )
                        }
                        composable ("add_tip/{gameId}"){
                            val gameId = it.arguments?.getString("gameId")?:""

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
