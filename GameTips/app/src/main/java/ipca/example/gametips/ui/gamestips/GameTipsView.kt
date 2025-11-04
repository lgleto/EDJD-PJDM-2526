package ipca.example.gametips.ui.gamestips

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.gametips.ui.theme.GameTipsTheme


@Composable
fun GameTipsView(
    modifier: Modifier = Modifier,
    navController : NavController
){

    val viewModel : GameTipsViewModel = viewModel()
    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadGames()
    }

    Box (
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ){
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            itemsIndexed(
                items = uiState.games,
            ) { index, game ->
                GameTipCellView(game = game) {

                }
            }
        }
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = {
                navController.navigate("add_game")
        }) {
            Text("Add Game")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GameTipsViewPreview() {
    GameTipsTheme {
        GameTipsView(
            navController = rememberNavController()
        )
    }
}