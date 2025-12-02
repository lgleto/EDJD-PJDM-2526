package ipca.example.gametips.repositories

import android.util.Log
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import ipca.example.gametips.TAG
import ipca.example.gametips.models.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val db: FirebaseFirestore
) {
    fun getAll() : Flow<ResultWrapper<List<Game>>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .snapshotFlow()
                .collect { snapshot ->
                    val games = mutableListOf<Game>()
                    for (document in snapshot.documents) {
                        val game = document.toObject<Game>()
                        game?.docId = document.id
                        if (game != null)
                            games.add(game)
                    }
                    emit(ResultWrapper.Success(games.toList()))
                }
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)

    fun get(id: String) : Flow<ResultWrapper<Game?>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .document(id)
                .snapshotFlow()
                .collect { snapshot ->
                    val game = snapshot.toObject<Game>()
                    game?.docId = snapshot.id
                    emit(ResultWrapper.Success(game))
                }
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)

    fun add(game: Game) : Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .add(game)
                .await()
            emit(ResultWrapper.Success(Unit))
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)

    fun update(game: Game) : Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .document(game.docId!!)
                .set(game)
                .await()
            emit(ResultWrapper.Success(Unit))
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)

    fun delete(game: Game) : Flow<ResultWrapper<Unit>> = flow {
        try {
            emit(ResultWrapper.Loading())
            db.collection("games")
                .document(game.docId!!)
                .delete()
                .await()
            emit(ResultWrapper.Success(Unit))
        }catch (e: Exception) {
            emit(ResultWrapper.Error(e.message?:""))
        }
    }.flowOn(Dispatchers.IO)


}