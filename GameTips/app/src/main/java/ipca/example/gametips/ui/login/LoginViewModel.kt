package ipca.example.gametips.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import ipca.example.gametips.TAG
import ipca.example.gametips.repositories.AuthRepository
import ipca.example.gametips.repositories.ResultWrapper
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

data class LoginState(
    var email : String = "",
    var password : String = "",
    var error : String? = null,
    var loading : Boolean = false
)
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var uiState = mutableStateOf(LoginState())
     private set

    fun onChangeEmail(email : String) {
        uiState.value = uiState.value.copy(
            email = email
        )
    }

    fun onChangePassword(password : String) {
        uiState.value = uiState.value.copy(
            password = password
        )
    }

    fun login(onLoginSuccess : () -> Unit) {

        if (uiState.value.email.isEmpty()) {
            uiState.value = uiState.value.copy(
                error = "Email is required"
            )
        }

        if (uiState.value.password.isEmpty()) {
            uiState.value = uiState.value.copy(
                error = "Password is required"
            )
        }

        authRepository.login(
            email = uiState.value.email,
            password = uiState.value.password
        ).onEach { result ->
            when(result) {
                is ResultWrapper.Success -> {
                    onLoginSuccess()
                    uiState.value = uiState.value.copy(
                        loading = false,
                        error = null
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
                        error = result.message?:""
                    )
                }
            }

        }.launchIn(viewModelScope)



    }

}