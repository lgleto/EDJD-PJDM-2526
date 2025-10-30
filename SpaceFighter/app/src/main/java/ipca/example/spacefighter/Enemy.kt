package ipca.example.spacefighter;

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import java.util.Random

public class Enemy {

    var x = 0
    var y = 0
    var speed = 0
    var maxY = 0
    var minY = 0
    var maxX = 0
    var minX = 0

    var bitmap: Bitmap

    var generator = Random()

    var collisionBox : Rect

    constructor(context: Context, with: Int, height: Int) {

        bitmap = BitmapFactory
            .decodeResource(
                context.resources,
                R.drawable.enemy)

        minX = 0
        maxX = with

        maxY = height - bitmap.height
        minY = 0

        speed = generator.nextInt(6) + 10

        x = maxX
        y = generator.nextInt(maxY)

        collisionBox = Rect(x, y, bitmap.width, bitmap.height)

    }

    fun update(playerSpeed : Int){
        x -= speed
        x -= playerSpeed

        if (x < -bitmap.width){
            x = maxX
            y = generator.nextInt(maxY)
            speed = generator.nextInt(6) + 10
        }

        collisionBox.left = x
        collisionBox.top = y
        collisionBox.right = x + bitmap.width
        collisionBox.bottom = y + bitmap.height
    }


}
