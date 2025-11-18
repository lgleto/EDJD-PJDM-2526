package ipca.example.gametips.ui.tips

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.gametips.R
import ipca.example.gametips.models.Tip
import ipca.example.gametips.ui.theme.GameTipsTheme


@Composable
fun TipsView(
    modifier: Modifier = Modifier,
    navController : NavController,
    viewModel : TipsViewModel = viewModel(),
    gameId : String
){


    val uiState by viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.loadTips(gameId)
    }

    Column  (
        modifier = modifier
            .fillMaxSize(),

    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            itemsIndexed(
                items = uiState.tips,
            ) { index, tip ->
                TipCellView(
                    tip = tip,
                    user = viewModel.getUserWithId(tip.userId?:"")
                    ) {

                }
            }
        }
        Row (
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center){
            TextField(
                modifier = Modifier,
                value = uiState.newTip,
                onValueChange = {
                    viewModel.onChangeNewTip(it)
                },
                label = {
                    Text("Add your tip here...")
                }
            )
            Image(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(CircleShape)
                    .size(52.dp)
                    .background(Color.DarkGray)
                    .padding(8.dp)
                    .clickable{
                        viewModel.addTip( gameId = gameId)
                    }
                ,
                painter = painterResource(R.drawable.outline_send_24),
                contentDescription = "Add Tip"
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameTipsViewPreview() {
    GameTipsTheme {
        TipsView(
            navController = rememberNavController(),

            gameId = "gameId",
            viewModel = viewModel<TipsViewModel>().apply{
                uiState.value = TipsState(
                    tips = listOf(
                        Tip(
                            comment = "Comment 1",
                            userId = "User 1"
                        ),
                        Tip(
                            comment = "Comment 2",
                            userId = "User 2"
                        ),
                    )
                )
            }
        )
    }
}