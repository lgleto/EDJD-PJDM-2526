package ipca.example.lastnews.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import ipca.example.lastnews.ui.theme.LastNewsTheme

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
                navController.navigate("bloomberg")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.MailOutline,
                    contentDescription = "Bloomberg"
                )
            },
            label = {
                Text("Bloomberg")
            }
        )
        NavigationBarItem(
            selected = clickedIndex == 1,
            onClick = {
                clickedIndex = 1
                navController.navigate("espn")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "espn"
                )
            },
            label = {
                Text("ESPN")
            }
        )
        NavigationBarItem(
            selected = clickedIndex == 2,
            onClick = {
                clickedIndex = 2
                navController.navigate("techcrunch")
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "techcrunch"
                )
            },
            label = {
                Text("TechCrunch")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MyBottomBarPreview(){
    LastNewsTheme {
        MyBottomBar(
            navController = rememberNavController()
        )
    }
}