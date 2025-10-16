package ipca.example.carts.models

import org.json.JSONObject

data class Cart (
    var id              : Int? = null,
    var products        : List<Product>? = null,
    var total           : Double? = null,
    var discountedTotal : Double? = null,
    var totalProducts   : Int? = null,
    var totalQuantity   : Int? = null,
){

    companion object{
        fun fromJSON(json : JSONObject) : Cart {
            val cart = Cart()
            cart.id                   = json.getInt   ("id"                   )
            val productList : MutableList<Product> = arrayListOf()
            val jsonArrayProducts  = json.getJSONArray("products" )
            for ( index in 0..<jsonArrayProducts.length()){
                val productJson = jsonArrayProducts.get(index) as JSONObject
                val product = Product.fromJSON(productJson)
                productList.add(product)
            }
            cart.products             = productList
            cart.total                = json.getDouble("total"                )
            cart.discountedTotal      = json.getDouble("discountedTotal"      )
            cart.totalProducts        = json.getInt   ("totalProducts"        )
            cart.totalQuantity        = json.getInt   ("totalQuantity"        )
            return cart
        }
    }
}