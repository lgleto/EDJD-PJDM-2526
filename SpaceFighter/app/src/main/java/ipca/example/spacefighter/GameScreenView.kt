package ipca.example.spacefighter

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import ipca.example.spacefighter.ui.theme.SpaceFighterTheme

@Composable
fun GameScreenView (
    modifier: Modifier = Modifier
){
    AndroidView(
        factory = { GameView(it, 600, 800) },
    ){
        it.resume()
    }



}

@Preview(showBackground = true)
@Composable
fun GameScreenViewPreview(){
    SpaceFighterTheme {
        GameScreenView()
    }
}