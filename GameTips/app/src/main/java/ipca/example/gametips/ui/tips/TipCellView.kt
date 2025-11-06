package ipca.example.gametips.ui.tips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ipca.example.gametips.models.Tip
import ipca.example.gametips.ui.theme.GameTipsTheme

@Composable
fun TipCellView(
    modifier: Modifier = Modifier,
    tip : Tip,
    onClick : () -> Unit = {}
){
    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{
                onClick()
            }
    ){
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Text(tip.comment?:"",
                style = TextStyle(
                    fontSize = 20.sp,
                ),
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TipCellViewPreview() {
    GameTipsTheme {
        TipCellView(
           tip = Tip(
               comment = "Comment 1"
           )
        )
    }
}