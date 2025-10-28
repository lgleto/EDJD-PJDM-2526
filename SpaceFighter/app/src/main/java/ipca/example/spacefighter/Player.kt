package ipca.example.spacefighter;

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

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
    var bitmap: Bitmap? = null

    constructor(context: Context, with: Int, height: Int) {

        bitmap = BitmapFactory
            .decodeResource(
                context.resources,
                R.drawable.player)

        minX = 0
        maxX = with

        maxY = height - bitmap!!.height
        minY = 0

        speed = 20

        x = 75
        y = 50

    }

    fun update(){

        if (isBoosting){
            y = y + speed
        }else {
            y = y + GRAVITY
        }

        if (y < minY){
            y = minY
        }

        if (y > maxY) {
            y = maxY
        }
    }


}
