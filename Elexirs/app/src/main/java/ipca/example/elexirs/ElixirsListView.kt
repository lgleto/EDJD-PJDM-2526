package ipca.example.elexirs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ElixirsListView(){
    val viewModel : ElixirsListViewModel = viewModel()
    val uiState by viewModel.uiState

    Text("djdfhjdjhfdfjh")

    LaunchedEffect(Unit) {
        viewModel.load()
    }
}