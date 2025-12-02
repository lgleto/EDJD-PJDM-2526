package ipca.example.gametips.ui.tips

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.gametips.models.Tip
import ipca.example.gametips.models.User
import ipca.example.gametips.repositories.ResultWrapper
import ipca.example.gametips.repositories.TipsRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class TipsState(
    var tips : List<Tip> = emptyList(),
    var newTip : String = "",
    var error : String? = null,
    var loading : Boolean = false
)

@HiltViewModel
class TipsViewModel @Inject constructor(
    val tipsRepository: TipsRepository
) : ViewModel() {
    var uiState = mutableStateOf(TipsState())
        private set

    fun onChangeNewTip(newTip : String) {
        uiState.value = uiState.value.copy(
            newTip = newTip
        )
    }


    fun loadTips(gameId : String) {
        tipsRepository.getAll(gameId).onEach { result ->
            when(result) {
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        loading = false,
                        error = null,
                        tips = result.data?: emptyList()
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

    fun addTip(gameId: String){
        if (uiState.value.newTip.isEmpty()) {
            return
        }

        val tip = Tip(
            comment = uiState.value.newTip,
            userId = Firebase.auth.currentUser?.uid
        )

        tipsRepository.add(gameId, tip).onEach { result ->
            when(result) {
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        loading = false,
                        error = null,
                        newTip = ""
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

    fun deleteTip(gameId : String, tip: Tip){

        tipsRepository.delete(gameId, tip).onEach { result ->
            when(result) {
                is ResultWrapper.Success -> {
                    uiState.value = uiState.value.copy(
                        loading = false,
                        error = null,
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


