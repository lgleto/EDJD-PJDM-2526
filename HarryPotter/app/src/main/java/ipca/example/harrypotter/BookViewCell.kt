package ipca.example.harrypotter

import Book
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ipca.example.harrypotter.ui.theme.HarryPotterTheme
import kotlin.Int
import kotlin.String

@Composable
fun BookViewCell(
    book: Book,
    modifier: Modifier = Modifier,
    onClickBook: () -> Unit = {}
){
    Card(
        modifier  = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable{
                onClickBook()
            }
    ) {
        Column (
            modifier = Modifier.padding(8.dp)
        ){

            Text(
                text = "${book.title ?: ""}",
                fontSize = TextUnit(value = 24f, type = TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )
            Row(modifier = Modifier.padding(8.dp)) {
                AsyncImage(
                    model = book.cover,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp)
                )
                Text(text = book.description?.let {
                    if (it.length > 150) "${it.take(150)}..." else it
                } ?: "")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookViewCellPreview(){
    HarryPotterTheme {
        BookViewCell(
            book = Book(
                title = "Harry Potter and the Philosopher",
                number = 1,
                originalTitle = "Harry Potter and the Philosopher",
                releaseDate = "1997-06-26",
                description  = "dslfskjfsdkafjhasf askjdjkawdhs",
                pages = 1000,
                cover = "http:/image.url/a.jpg",
                index = 1
            )
        )
    }
}