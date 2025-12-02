package ipca.example.gametips.ui.gamestips

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.gametips.models.Game
import ipca.example.gametips.repositories.GameRepository
import ipca.example.gametips.repositories.ResultWrapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class GamesTipsState(
    var games : List<Game> = emptyList(),
    var error : String? = null,
    var loading : Boolean = false
)

@HiltViewModel
class GameTipsViewModel @Inject constructor(
    val gameRepository: GameRepository
) : ViewModel() {
    var uiState = mutableStateOf(GamesTipsState())
        private set

    fun loadGames() {
        gameRepository.getAll().onEach { result ->
            when(result) {
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        loading = false,
                        error = null,
                        games = result.data?: emptyList()
                    )
                }
                is ResultWrapper.Loading-> {
                    uiState.value = uiState.value.copy(
                        loading = true,
                        error = null
                    )
                }
                is ResultWrapper.Error -> {
                    uiState.value = uiState.value.copy(
                        loading = false,
                        error = result.message?:"unknown error"
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

}


