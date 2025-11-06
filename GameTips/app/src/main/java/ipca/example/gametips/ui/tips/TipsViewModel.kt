package ipca.example.gametips.ui.tips

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import ipca.example.gametips.models.Tip

data class TipsState(
    var tips : List<Tip> = emptyList(),
    var newTip : String = "",
    var error : String? = null,
    var loading : Boolean = false
)

class TipsViewModel : ViewModel() {
    var uiState = mutableStateOf(TipsState())
        private set

    fun onChangeNewTip(newTip : String) {
        uiState.value = uiState.value.copy(
            newTip = newTip
        )
    }

    fun loadTips(gameId : String) {
        uiState.value = uiState.value.copy(
            loading = true
        )

        val db = Firebase.firestore
        db.collection("games")
            .document(gameId)
            .collection("tips")
            .addSnapshotListener { value, error ->

                if (error != null) {
                    uiState.value = uiState.value.copy(
                        error = error.message,
                        loading = false
                    )
                    return@addSnapshotListener
                }

                val tips = mutableListOf<Tip>()
                for (document in value?.documents?:emptyList()) {
                    val tip = document.toObject<Tip>()
                    tip?.docId = document.id
                    if (tip != null)
                        tips.add(tip)
                }
                uiState.value = uiState.value.copy(
                    tips = tips,
                    loading = false
                )
            }
    }

    fun addTip(gameId: String){
        if (uiState.value.newTip.isEmpty()) {
            return
        }

        val tip = Tip(
            comment = uiState.value.newTip,
            userId = Firebase.auth.currentUser?.uid
        )

        val db = Firebase.firestore
        db.collection("games")
            .document(gameId)
            .collection("tips")
            .add(tip)
            .addOnSuccessListener {
                uiState.value = uiState.value.copy(
                    newTip = ""
                )
            }
    }
}


