package ipca.example.gametips.ui.gamestips

import android.content.Context
import android.net.Uri
import androidx.activity.result.launch
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.gametips.models.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.util.Base64
data class AddGameState(
    var name : String = "",
    var description : String = "",
    var coverUri : Uri? = null,
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

    fun addGame(context : Context, onAddGameSuccess : () -> Unit) {
        viewModelScope.launch {
            val currentUser = Firebase.auth.currentUser
            var coverBase64: String? = null

            // Perform the conversion in a background thread
            if (uiState.value.coverUri != null) {
                withContext(Dispatchers.IO) {
                    try {
                        val inputStream =
                            context.contentResolver.openInputStream(uiState.value.coverUri!!)
                        val bytes = inputStream?.readBytes()
                        inputStream?.close()
                        bytes?.let {
                            coverBase64 = Base64.encodeToString(it, Base64.DEFAULT)
                        }
                    } catch (e: Exception) {
                        uiState.value = uiState.value.copy(error = e.message, loading = false)
                        return@withContext // Stop if conversion fails
                    }
                }
            }

            // Create the game object with the converted Base64 string
            val game = Game(
                name = uiState.value.name,
                description = uiState.value.description,
                userId = currentUser?.uid,
                cover = coverBase64 // Add the base64 string to your model
            )

            // Continue with Firestore operation on the main thread
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

    fun onCoverUriChange(newValue: Uri) {
        uiState.value = uiState.value.copy(coverUri = newValue)
    }

}