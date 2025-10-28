import org.json.JSONObject

data class Book (
    var number : Int? = null,
    var title : String? = null,
    var originalTitle : String? = null,
    var releaseDate : String? = null,
    var description : String? = null,
    var pages : Int? = null,
    var cover : String? = null,
    var index : Int? = null
){
    companion object {
        fun fromJson(jsonObject: JSONObject) : Book {
            val book = Book()
            book.number = jsonObject.getInt("number")
            book.title = jsonObject.getString("title")
            book.originalTitle = jsonObject.getString("originalTitle")
            book.releaseDate = jsonObject.getString("releaseDate")
            book.description = jsonObject.getString("description")
            book.pages = jsonObject.getInt("pages")
            book.cover = jsonObject.getString("cover")
            book.index = jsonObject.getInt("index")
            return book
        }
    }
}

