package ipca.example.lastnews.ui.articles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import ipca.example.lastnews.models.Article
import ipca.example.lastnews.ui.theme.LastNewsTheme


@Composable
fun ArticleViewCell(
    article: Article,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card (
        modifier = modifier
            .fillMaxWidth()
            .clickable{
                onClick()
            }
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(article.title ?: "",
                fontSize = TextUnit(18f, TextUnitType.Sp)
            )
            AsyncImage(
                model = article.urlToImage,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.padding(8.dp))
            Text(article.description ?: "")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleViewCellPreview() {
    LastNewsTheme {
        ArticleViewCell(
            article = Article(
                author = "Author",
                title = "Title",
                description = "Description",
            )
        )
    }
}