package ipca.example.spacefighter

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import ipca.example.spacefighter.ui.theme.SpaceFighterTheme

@Composable
fun GameScreenView (
    modifier: Modifier = Modifier
){

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp

    val density = configuration.densityDpi / 160f
    val screenHeightPx = screenHeight.value * density
    val screenWidthPx = screenWidth.value * density


    AndroidView(
        factory = { GameView(it,
            screenWidthPx.toInt(),
            screenHeightPx.toInt()) },
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