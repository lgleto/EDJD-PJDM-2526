package ipca.example.gametips.ui.gamestips

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import ipca.example.gametips.ui.theme.GameTipsTheme


@Composable
fun AddGameView(
    modifier: Modifier = Modifier,
    navController : NavController
){

    val context = LocalContext.current
    val viewModel : AddGameViewModel = viewModel()
    val uiState by viewModel.uiState

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                viewModel.onCoverUriChange(uri)
            }
        }

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .background(Color.DarkGray),
            model = uiState.coverUri,
            contentDescription = null,
        )

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                pickImageLauncher.launch("image/*")
            }) {
            Text("Photo")
        }
        TextField(
            modifier = Modifier.padding(8.dp),
            value = uiState.name,
            onValueChange = {
                viewModel.onChangeName(it)
            },
            label = {
                Text("name")
            }
        )
        TextField(
            modifier = Modifier.padding(8.dp),
            value = uiState.description,
            onValueChange = {
                viewModel.onChangeDescription(it)
            },
            label = {
                Text("description")
            }
        )

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                viewModel.addGame(context, onAddGameSuccess = {
                    navController.popBackStack()
                })
            }) {
            Text("Add Game")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LoginViewPreview() {
    GameTipsTheme {
        AddGameView(
            navController = rememberNavController()
        )
    }
}