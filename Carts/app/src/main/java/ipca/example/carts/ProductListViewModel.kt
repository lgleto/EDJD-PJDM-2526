package ipca.example.carts

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ipca.example.carts.models.Cart
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException



data class ProductListState(
    val cart: Cart? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProductListViewModel : ViewModel() {

    var uiState = mutableStateOf(ProductListState())
        private set



    fun fetch(id : String) {
        uiState.value = uiState.value.copy(isLoading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://dummyjson.com/carts/${id}")
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

                    val cart = Cart.fromJSON(jsonResult)
                        uiState.value = uiState.value.copy(
                            cart = cart,
                            isLoading = false,
                            error = null
                        )

                }
            }
        })
    }
}