package ipca.example.gametips.ui.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import ipca.example.gametips.TAG
import ipca.example.gametips.models.User

data class ProfileState(
    var name : String = "",
    var email : String = "",
    var bio : String = "",
    var hasChange : Boolean = false,
    var error : String? = null,
    var loading : Boolean = false
)

class ProfileViewModel : ViewModel() {

    var uiState = mutableStateOf(ProfileState())
     private set

    fun onChangeEmail(email : String) {
        uiState.value = uiState.value.copy(
            email = email,
            hasChange = true
        )
    }

    fun onChangeName(name : String) {
        uiState.value = uiState.value.copy(
            name = name,
            hasChange = true
        )
    }

    fun onChangeBio(bio : String) {
        uiState.value = uiState.value.copy(
            bio = bio,
            hasChange = true
        )
    }

    fun loadUser(){
        uiState.value = uiState.value.copy(
            loading = true
        )
        val uid = Firebase.auth.currentUser?.uid!!
        val db = Firebase.firestore

        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)
                uiState.value = uiState.value.copy(
                    name = user?.name?:"",
                    email = user?.email?:"",
                    bio = user?.bio?:"",
                    loading = false
                )
                if (user?.email.isNullOrEmpty()) {
                    val currentUserEmail = Firebase.auth.currentUser?.email
                    uiState.value = uiState.value.copy(
                        email = currentUserEmail ?: "",
                        loading = false
                    )
                    saveChanges()
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)

            }

    }

    fun saveChanges() {
        uiState.value = uiState.value.copy(
            loading = true
        )

        val uid = Firebase.auth.currentUser?.uid!!
        val db = Firebase.firestore

        val user = User(
            name = uiState.value.name,
            email = uiState.value.email,
            bio = uiState.value.bio
            )

        db.collection("users")
            .document(uid)
            .set(user)
            .addOnSuccessListener { documentReference ->
                uiState.value = uiState.value.copy(
                    loading = false,
                    hasChange = false
                )
             }


    }

}