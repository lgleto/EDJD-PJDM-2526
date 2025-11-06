package ipca.example.lastnews.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.gametips.ui.theme.GameTipsTheme

@Composable
fun MyBottomBar (
    navController: NavController
){
    var clickedIndex by remember { mutableStateOf(0) }
    BottomAppBar {
        NavigationBarItem(
            selected = clickedIndex == 0,
            onClick = {
                clickedIndex = 0
                navController.navigate("home")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Home"
                )
            },
            label = {
                Text("Home")
            }
        )
        NavigationBarItem(
            selected = clickedIndex == 1,
            onClick = {
                clickedIndex = 1
                navController.navigate("profile")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "profile"
                )
            },
            label = {
                Text("Profile")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomBarPreview(){
    GameTipsTheme {
        MyBottomBar(
            navController = rememberNavController()
        )
    }
}