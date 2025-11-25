package ipca.example.lastnews.ui.articles

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import ipca.example.lastnews.models.AppDatabase
import ipca.example.lastnews.models.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException

data class ArticlesFavoritesListState(
    val articles: List<Article> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ArticlesFavoritesListViewModel : ViewModel() {

    var uiState = mutableStateOf(ArticlesFavoritesListState())
        private set

    fun loadArticles(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val articles = AppDatabase
                .getInstance(context)
                ?.articleDao()
                ?.getAll()
            viewModelScope.launch(Dispatchers.Main) {
                uiState.value = uiState.value.copy(
                    articles = articles ?: emptyList(),
                    isLoading = false,
                    error = null
                )
            }
        }
    }
}