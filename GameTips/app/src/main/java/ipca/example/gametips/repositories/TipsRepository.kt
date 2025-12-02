package ipca.example.gametips.repositories

import android.util.Log
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import ipca.example.gametips.TAG
import ipca.example.gametips.models.Game
import ipca.example.gametips.models.Tip
import ipca.example.gametips.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TipsRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    fun getAll(gameId:String) : Flow<ResultWrapper<List<Tip>>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .document(gameId)
                .collection("tips")
                .snapshotFlow()
                .collect { snapshot ->
                    val tips = mutableListOf<Tip>()
                    for (document in snapshot.documents) {
                        val tip = document.toObject<Tip>()
                        tip?.docId = document.id

                        if (tip != null) {
                            val snapshotUser = db.collection("users")
                                .document(tip.userId!!)
                                .get()
                                .await()

                            val user = snapshotUser.toObject<User>()
                            user?.docId = snapshotUser.id

                            tip.user = user

                            tips.add(tip)
                        }
                    }
                    emit(ResultWrapper.Success(tips.toList()))
                }
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)

    fun get(gameId: String, id: String) : Flow<ResultWrapper<Tip?>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .document(gameId)
                .collection("tips")
                .document(id)
                .snapshotFlow()
                .collect { snapshot ->
                    val tip = snapshot.toObject<Tip>()
                    tip?.docId = snapshot.id

                    if (tip != null) {
                        val snapshotUser = db.collection("users")
                            .document(tip.userId!!)
                            .get()
                            .await()

                        val user = snapshotUser.toObject<User>()
                        user?.docId = snapshotUser.id
                        tip.user = user
                    }

                    emit(ResultWrapper.Success(tip))
                }
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)

    fun add(gameId:String, tip: Tip) : Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .document(gameId)
                .collection("tips")
                .add(tip)
                .await()
            emit(ResultWrapper.Success(Unit))
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)

    fun update(gameId:String, tip: Tip) : Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .document(gameId)
                .collection("tips")
                .document(tip.docId!!)
                .set(tip)
                .await()
            emit(ResultWrapper.Success(Unit))
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)

    fun delete(gameId: String, tip: Tip) : Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .document(gameId)
                .collection("tips")
                .document(tip.docId!!)
                .delete()
                .await()
            emit(ResultWrapper.Success(Unit))
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)


}