package ipca.example.harrypotter

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun BookListView(
    modifier: Modifier = Modifier,
    navController : NavController
) {
    val context = LocalContext.current
    val viewModel : BookListViewModel = viewModel()
    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.fetchBooks()
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = uiState.books
        ){book ->
            BookViewCell(
                book = book){
                Toast.makeText(context,
                    (book.title ?: ""),
                    Toast.LENGTH_SHORT)
                    .show( )

                navController.navigate("bookDetail")
                }

        }
    }

}