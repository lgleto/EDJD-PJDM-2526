package ipca.example.harrypotter

import Book
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONArray
import java.io.IOException

data class BookListState (
    var books: List<Book> = emptyList()
)

class BookListViewModel : ViewModel() {

    var uiState by mutableStateOf(BookListState())
        private set

    fun fetchBooks(){
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://potterapi-fedeperin.vercel.app/en/books")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val result = response.body!!.string()
                    val jsonArray = JSONArray(result)
                    val books = mutableListOf<Book>()
                    for (index in 0..<jsonArray.length()) {
                        val jsonBook = jsonArray.getJSONObject(index)
                        val book = Book.fromJson(jsonBook)
                        books.add(book)
                    }
                    uiState = uiState.copy(
                        books = books
                    )
                }
            }
        })

    }

}