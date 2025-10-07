package ipca.example.lastnews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import ipca.example.lastnews.models.Article
import ipca.example.lastnews.ui.theme.LastNewsTheme
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


@Composable
fun ArticlesListView(modifier: Modifier = Modifier) {

    var articles by remember { mutableStateOf(arrayListOf<Article>())  }

    LazyColumn(modifier = modifier) {
        itemsIndexed(
            articles
        ){ index, article  ->
            Card (
                modifier = Modifier.padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(article.title ?: "",
                        fontSize = TextUnit(18f, TextUnitType.Sp)
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Text(article.description ?: "")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=1765f87e4ebc40229e80fd0f75b6416c")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

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
                        articles = articlesResult
                    }
                }
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun ArticlesListViewPreview() {
    LastNewsTheme {
        ArticlesListView()
    }
}