package ipca.example.gametips.models

import com.google.firebase.firestore.Exclude

data class Tip (
    var docId : String? = null,
    var comment : String? =null,
    var userId : String? = null
){

    @Exclude
    var user : User? = null
}