package ipca.example.carts.models

import org.json.JSONObject

data class Product (
    var id                  : Int? = null ,
    var title               : String? = null,
    var price               : Double? = null,
    var quantity            : Int? = null,
    var total               : Double? = null,
    var discountPercentage  : Double? = null,
    var discountedTotal     : Double? = null,
    var thumbnail           : String? = null
){

    companion object{
        fun fromJSON(json : JSONObject) : Product {
            val product = Product()
            product.id                   = json.getInt      ("id"                   )
            product.title                = json.getString   ("title"                )
            product.price                = json.getDouble   ("price"                )
            product.quantity             = json.getInt      ("quantity"             )
            product.total                = json.getDouble   ("total"                )
            product.discountPercentage   = json.getDouble   ("discountPercentage"   )
            product.discountedTotal      = json.getDouble   ("discountedTotal"      )
            product.thumbnail            = json.getString   ("thumbnail"            )
            return product
        }
    }
}