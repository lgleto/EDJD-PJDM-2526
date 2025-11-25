package ipca.example.lastnews.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import org.json.JSONObject
import java.net.URLDecoder
import java.net.URLEncoder

@Entity
data class Article (
    var author      : String? = null,
    var title       : String? = null,
    var description : String? = null,
    @PrimaryKey
    var url         : String = "",
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

@Dao
interface ArticleDao{

    @Query("SELECT * FROM article")
    fun getAll() : List<Article>

    @Query("SELECT * FROM article WHERE url = :url")
    fun getByUrl(url : String) : Article?

    @Delete
    fun delete(article: Article)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

}

fun String.encodeUrl() : String {
    return URLEncoder.encode(this, "UTF-8")
}

fun String.decodeUrl() : String {
    return URLDecoder.decode(this, "UTF-8")
}