package ipca.example.gametips.ui.gamestips

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import ipca.example.gametips.models.Game

data class GamesTipsState(
    var games : List<Game> = emptyList(),
    var error : String? = null,
    var loading : Boolean = false
)

class GameTipsViewModel : ViewModel() {
    var uiState = mutableStateOf(GamesTipsState())
        private set

    fun loadGames() {
        uiState.value = uiState.value.copy(
            loading = true
        )
        val db = Firebase.firestore
        db.collection("games")
            .addSnapshotListener { value, error ->

                if (error != null) {
                    uiState.value = uiState.value.copy(
                        error = error.message,
                        loading = false
                    )
                    return@addSnapshotListener
                }

                val games = mutableListOf<Game>()
                for (document in value?.documents?:emptyList()) {
                    val game = document.toObject<Game>()
                    game?.docId = document.id
                    if (game != null)
                        games.add(game)
                }
                uiState.value = uiState.value.copy(
                    games = games,
                    loading = false
                )
            }

    }

}


