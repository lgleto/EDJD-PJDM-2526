package ipca.example.lastnews

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.example.lastnews.models.Article
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class ArticlesListState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ArticlesListViewModel : ViewModel() {

    var uiState = mutableStateOf(ArticlesListState())
        private set

    fun loadArticles() {
        uiState.value = uiState.value.copy(isLoading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=1765f87e4ebc40229e80fd0f75b6416c")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                uiState.value = uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) {
                        uiState.value = uiState.value.copy(
                            isLoading = false,
                            error = "Error: ${response.code}"
                        )
                        return
                    }

                    val result = response.body!!.string()
                    val jsonResult = JSONObject(result)
                    val status = jsonResult.getString("status")
                    val articlesResult = arrayListOf<Article>()
                    if (status == "ok") {
                        val articlesJSONArray = jsonResult.getJSONArray("articles")
                        for (i in 0..<articlesJSONArray.length()) {
                            val articleJSONObject = articlesJSONArray.getJSONObject(i)
                            val article = Article.fromJSON(articleJSONObject)
                            articlesResult.add(article)
                        }
                        uiState.value = uiState.value.copy(
                            articles = articlesResult,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            }
        })
    }
}