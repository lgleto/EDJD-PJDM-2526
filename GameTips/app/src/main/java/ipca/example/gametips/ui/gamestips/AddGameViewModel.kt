package ipca.example.gametips.ui.gamestips

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.gametips.models.Game

data class AddGameState(
    var name : String = "",
    var description : String = "",
    var error : String? = null,
    var loading : Boolean = false
)

class AddGameViewModel : ViewModel() {

    var uiState = mutableStateOf(AddGameState())
        private set

    fun onChangeName(name : String) {
        uiState.value = uiState.value.copy(
            name = name
        )
    }

    fun onChangeDescription(description : String) {
        uiState.value = uiState.value.copy(
            description = description
        )
    }

    fun addGame(onAddGameSuccess : () -> Unit) {

        val currentUser = Firebase.auth.currentUser


        val game = Game(
            name = uiState.value.name,
            description = uiState.value.description,
            userId = currentUser?.uid
        )

        val db = Firebase.firestore

        db.collection("games")
            .add(game)
            .addOnSuccessListener {
                    onAddGameSuccess()
            }
            .addOnFailureListener {
                uiState.value = uiState.value.copy(
                    error = it.message,
                    loading = false
                )
            }


    }

}