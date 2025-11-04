package ipca.example.spacefighter;

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect

public class Player {

    var x = 0
    var y = 0
    var speed = 0
    var maxY = 0
    var minY = 0
    var maxX = 0
    var minX = 0
    var isBoosting = false
    val GRAVITY = -10
    var bitmap: Bitmap
    val MAX_SPEED = 20
    val MIN_SPEED = 1

    var width : Int
    var height : Int

    var collisionBox : Rect

    constructor(context: Context, with: Int, height: Int) {

        bitmap = BitmapFactory
            .decodeResource(
                context.resources,
                R.drawable.player)

       this.width = bitmap.width
       this.height = bitmap.height


        minX = 0
        maxX = with

        maxY = height - bitmap.height
        minY = 0

        speed = 20

        x = 75
        y = 50

        collisionBox = Rect(x, y, bitmap.width, bitmap.height)


    }

    fun update(){

        if (isBoosting) speed += 2
        else speed -= 5
        if (speed>MAX_SPEED) speed = MAX_SPEED
        if (speed<MIN_SPEED) speed = MIN_SPEED

        y -= speed + GRAVITY

        if (y < minY) y = minY
        if (y > maxY) y = maxY

        collisionBox.left = x
        collisionBox.top = y
        collisionBox.right = x + bitmap.width
        collisionBox.bottom = y + bitmap.height


    }


}
