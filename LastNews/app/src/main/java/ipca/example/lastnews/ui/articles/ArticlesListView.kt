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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import ipca.example.lastnews.models.Article
import ipca.example.lastnews.models.encodeUrl
import ipca.example.lastnews.ui.theme.LastNewsTheme


@Composable
fun ArticlesListView(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val viewModel : ArticlesListViewModel = viewModel()
    val uiState by viewModel.uiState


    ArticlesListViewContent(
        uiState = uiState,
        modifier = modifier,
        navController = navController
    )

    LaunchedEffect(Unit) {
        viewModel.loadArticles()
    }
}

@Composable
fun ArticlesListViewContent(
    modifier: Modifier = Modifier,
    uiState : ArticlesListState,
    navController: NavController
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
                        navController.navigate("articles_detail/${article.url?.encodeUrl()}")
                    }
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
            navController = rememberNavController(),
            uiState = ArticlesListState(
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