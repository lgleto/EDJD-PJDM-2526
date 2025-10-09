package ipca.example.lastnews.models

import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder

data class Article (
    var author      : String? = null,
    var title       : String? = null,
    var description : String? = null,
    var url         : String? = null,
    var urlToImage  : String? = null,
    var publishedAt : String? = null,
    ){

    companion object{
        fun fromJSON(json : JSONObject) : Article {
            val article = Article()
            article.author      = json.getString("author"      )
            article.title       = json.getString("title"       )
            article.description = json.getString("description" )
            article.url         = json.getString("url"         )
            article.urlToImage  = json.getString("urlToImage"  )
            article.publishedAt = json.getString("publishedAt" )

            return article
        }
    }
}

fun String.encodeUrl() : String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.decodeUrl() : String {
    return URLDecoder.decode(this, "UTF-8")
}