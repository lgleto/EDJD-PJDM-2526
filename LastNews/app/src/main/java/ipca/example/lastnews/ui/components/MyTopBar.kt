package ipca.example.lastnews.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.lastnews.ui.theme.LastNewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar (
    topBarTitle: String,
    isHomeScreen: Boolean,
    navController: NavController,
    onClickFavorite: () -> Unit = {}
){
    TopAppBar(
        title = { Text(topBarTitle) },
        actions = {
            if (!isHomeScreen)
                IconButton(onClick = onClickFavorite) {
                    Icon(
                        imageVector = Icons.Filled.FavoriteBorder,
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

@Preview(showBackground = true)
@Composable
fun MyTopBarPreview(){
    LastNewsTheme {
        MyTopBar(
            topBarTitle = "News",
            isHomeScreen = false,
            navController = rememberNavController()
        )
    }
}