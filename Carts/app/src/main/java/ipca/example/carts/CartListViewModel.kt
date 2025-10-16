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



data class CartListState(
    val carts: List<Cart> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class CartListViewModel : ViewModel() {

    var uiState = mutableStateOf(CartListState())
        private set



    fun fetch() {
        uiState.value = uiState.value.copy(isLoading = true)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://dummyjson.com/carts")
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

                    val cartsResult = arrayListOf<Cart>()

                        val cartsJSONArray = jsonResult.getJSONArray("carts")
                        for (i in 0..<cartsJSONArray.length()) {
                            val cartJSONObject = cartsJSONArray.getJSONObject(i)
                            val cart = Cart.fromJSON(cartJSONObject)
                            cartsResult.add(cart)
                        }
                        uiState.value = uiState.value.copy(
                            carts = cartsResult,
                            isLoading = false,
                            error = null
                        )

                }
            }
        })
    }
}