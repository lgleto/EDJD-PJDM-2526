package ipca.example.spacefighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.ui.geometry.Rect


class GameView : SurfaceView, Runnable {
    private var gameThread: Thread? = null
    private var isPlaying = false

    lateinit var surfaceHolder : SurfaceHolder
    lateinit var canvas: Canvas
    lateinit var paint: Paint

    private fun init (context: Context?, width: Int, height: Int){
        surfaceHolder = holder
        paint = Paint()
    }

    constructor(context: Context?,width: Int, height: Int) : super(context){
        init(context, width, height)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun run() {
        // gameloop
        while(isPlaying){
            update()
            draw()
            control()
        }
    }

    fun resume(){
        isPlaying = true
        gameThread = Thread(this)
        gameThread!!.start()
    }

    fun pause() {
        isPlaying = false
        gameThread?.join()
        gameThread = null
    }


    fun update() {

    }

    fun draw(){
        if (surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()

            canvas.drawColor(Color.RED)

            paint.color = Color.GREEN
            canvas.drawRect(RectF(100f, 100f, 200f, 200f), paint)

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun control(){
        Thread.sleep(17)
    }


}