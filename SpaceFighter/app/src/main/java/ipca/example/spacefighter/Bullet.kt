package ipca.example.spacefighter;

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.util.Random


public class Bullet {

    var x = 0
    var y = 0
    var speed = 0
    var maxY = 0
    var minY = 0
    var maxX = 0
    var minX = 0


    constructor(context: Context, with: Int, height: Int, playerX: Int, playerY: Int) {

        minX = 0
        maxX = with

        maxY = height
        minY = 0

        speed = 60

        x = playerX
        y = playerY

    }

    fun update(playerSpeed: Int){
        x += speed
        x += playerSpeed
    }


}
