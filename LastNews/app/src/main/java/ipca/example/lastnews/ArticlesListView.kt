package ipca.example.lastnews

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.example.lastnews.models.Article
import ipca.example.lastnews.ui.theme.LastNewsTheme


@Composable
fun ArticlesListView(modifier: Modifier = Modifier) {

    val viewModel : ArticlesListViewModel = viewModel()
    val uiState by viewModel.uiState


    ArticlesListViewContent(
        uiState = uiState,
        modifier = modifier
    )

    LaunchedEffect(Unit) {
        viewModel.loadArticles()
    }
}

@Composable
fun ArticlesListViewContent(
    modifier: Modifier = Modifier,
    uiState : ArticlesListState
) {

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else if (uiState.error != null) {
            Text(uiState.error)
        } else {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(
                    uiState.articles
                ) { index, article ->
                    ArticleViewCell(article)
                }
            }
        }
    }

}




@Preview(showBackground = true)
@Composable
fun ArticlesListViewPreview() {
    LastNewsTheme {
        ArticlesListViewContent(
            uiState = ArticlesListState(
                isLoading = false,
                error = null,
                articles = listOf(
                    Article(
                        author = "Author",
                        title = "Title",
                        description = "Description",
                    ),
                    Article(
                        author = "Author",
                        title = "Title",
                        description = "Description",
                    )
                )
            )
        )
    }
}