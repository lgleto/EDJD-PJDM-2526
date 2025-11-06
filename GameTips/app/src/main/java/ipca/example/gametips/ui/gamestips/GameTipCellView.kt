package ipca.example.gametips.ui.gamestips

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.gametips.models.Game
import ipca.example.gametips.ui.theme.GameTipsTheme

@Composable
fun GameTipCellView(
    modifier: Modifier = Modifier,
    game : Game,
    onClick : () -> Unit = {}
){
    val imageBitmap = remember(game.cover) {
        game.cover?.let { base64Str ->
            try {
                val decodedBytes = Base64.decode(base64Str,
                    Base64.DEFAULT)
                BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)?.asImageBitmap()
            } catch (e: IllegalArgumentException) {
                // Handle potential Base64 decoding errors
                null
            }
        }
    }
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick()
            }
    ){

        imageBitmap?.let {
            Image(
                bitmap = it,
                contentDescription = "${game.name} cover",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 180.dp) ,
                contentScale = ContentScale.Crop // Adjust how the image is scaled

            )
        }
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(game.name?:"",
                style = TextStyle(
                    fontSize = 20.sp,
                ),
                fontWeight = FontWeight.Bold
            )
            Text(game.description?:"")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GameTipCellViewPreview() {
    GameTipsTheme {
        GameTipCellView(
           game = Game(
               name = "Game 1",
               description = "Description 1",
           )
        )
    }
}