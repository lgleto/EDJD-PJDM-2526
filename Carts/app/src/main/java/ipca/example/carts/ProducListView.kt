package ipca.example.carts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage

@Composable
fun ProductListView (
    id : String
){

    val viewModel : ProductListViewModel = viewModel()
    val uiState by viewModel.uiState

    LazyColumn {
        itemsIndexed(
            items = uiState.cart?.products?:emptyList()
        ){ index, item ->
            Card (
                modifier = Modifier.padding(16.dp)
            ){
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = " ${item.title}")
                    AsyncImage(
                        model = item.thumbnail,
                        contentDescription = null,
                    )
                    Text(text = " ${item.price}")
                    Text(text = " ${item.total}")
                }

            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetch(id)
    }

}