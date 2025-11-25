package ipca.example.lastnews.ui.articles

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.lastnews.models.Article
import ipca.example.lastnews.models.encodeUrl
import ipca.example.lastnews.ui.theme.LastNewsTheme


@Composable
fun ArticlesFavoritesListView(
    navController: NavController,
    modifier: Modifier = Modifier,
    onArticleClick : (Article) -> Unit = {}
) {

    val viewModel : ArticlesFavoritesListViewModel = viewModel()
    val uiState by viewModel.uiState
    val context = LocalContext.current


    ArticlesFavortiesListViewContent(
        uiState = uiState,
        modifier = modifier,
        navController = navController,
        onArticleClick = onArticleClick
    )

    LaunchedEffect(Unit) {
        viewModel.loadArticles(context)
    }
}

@Composable
fun ArticlesFavortiesListViewContent(
    modifier: Modifier = Modifier,
    uiState : ArticlesFavoritesListState,
    navController: NavController,
    onArticleClick : (Article) -> Unit = {}
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
                    ArticleViewCell(article){
                        onArticleClick(article)
                        navController.navigate("articles_detail/${article.url?.encodeUrl()}")
                    }
                }
            }
        }
    }

}




@Preview(showBackground = true)
@Composable
fun ArticlesFavoritesListViewPreview() {
    LastNewsTheme {
        ArticlesFavortiesListViewContent(
            navController = rememberNavController(),
            uiState = ArticlesFavoritesListState(
                isLoading = false,
                error = null,
                articles = listOf(
                    Article(
                        author = "Author1",
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