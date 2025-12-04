package ipca.example.gametips

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import ipca.example.gametips.models.User
import ipca.example.gametips.ui.tips.TipsView
import ipca.example.gametips.ui.gamestips.AddGameView
import ipca.example.gametips.ui.gamestips.GameTipsView
import ipca.example.gametips.ui.login.LoginView
import ipca.example.gametips.ui.profile.ProfileView
import ipca.example.gametips.ui.theme.GameTipsTheme
import ipca.example.lastnews.ui.components.MyBottomBar
import ipca.example.lastnews.ui.components.MyTopBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

const val TAG = "GameTips"

val Context.dataStore by preferencesDataStore(name = "user_prefs")

object TokenPreferences {
    val TOKEN = stringPreferencesKey("auth_token")
}
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result


            val uid = Firebase.auth.currentUser?.uid
            uid?.let {
                val db = Firebase.firestore
                db.collection("users")
                    .document(uid)
                    .set(mapOf("token" to token), SetOptions.merge())
            }


            GlobalScope.launch (Dispatchers.IO){
                this@MainActivity.dataStore.edit { prefs ->
                    prefs[TokenPreferences.TOKEN] = token
                }
            }

            Log.d(TAG, "FCM token: $token")

        })
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var title by remember { mutableStateOf("Game Tips") }
            var isHome by remember { mutableStateOf(true) }
            val context = LocalContext.current
            val snackbarHostState = remember { SnackbarHostState() }
            val coroutineScope = rememberCoroutineScope()

            val permissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) {isGranted: Map<String, Boolean> ->
                Log.d("PERMISSIONS", "Launcher result: $isGranted")
                if (isGranted.containsValue(false)) {
                    Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,  "Permission granted", Toast.LENGTH_SHORT).show()
                }
            }

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
                    },
                    snackbarHost = {
                        SnackbarHost(snackbarHostState){data ->
                            Snackbar(
                                snackbarData = data,
                            )
                        }
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
                if (ActivityCompat.checkSelfPermission(
                        this@MainActivity,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ){
                    // request permissions
                    permissionLauncher.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
                }
            }

            DisposableEffect(context) {
                val intentFilter = IntentFilter("broadcast_message")
                val broadcastReceiver = object : BroadcastReceiver() {
                    override fun onReceive(context: Context?, intent: Intent?) {
                        if (intent?.action == "broadcast_message") {
                            val message = intent.getStringExtra("message")
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = message?:"no message",
                                    actionLabel = "Dismiss",
                                    withDismissAction = true,
                                    duration = SnackbarDuration.Long
                                )
                            }
                        }
                    }
                }

                ContextCompat.registerReceiver(
                    context,
                    broadcastReceiver,
                    intentFilter,
                    ContextCompat.RECEIVER_EXPORTED
                )

                onDispose {
                    context.unregisterReceiver(broadcastReceiver)
                }
            }
        }
    }
}
