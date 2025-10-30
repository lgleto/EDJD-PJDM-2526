package ipca.example.spacefighter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.media.AudioAttributes
import android.media.SoundPool

import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView : SurfaceView, Runnable {
    private var gameThread: Thread? = null
    private var isPlaying = false
    lateinit var surfaceHolder : SurfaceHolder
    lateinit var canvas: Canvas
    lateinit var paint: Paint
    lateinit var player : Player

    var stars = ArrayList<Star>()
    var enemies = ArrayList<Enemy>()

    lateinit var boom : Boom

    // 2. Declare SoundPool and sound ID variables
    private lateinit var soundPool: SoundPool
    private var soundIdExplosion: Int = 0

    private fun init (context: Context, width: Int, height: Int){
        surfaceHolder = holder
        paint = Paint()
        player = Player(context, width, height)
        for (i in 0..100){
            stars.add(Star(context, width, height))
        }
        for (i in 0..2){
            enemies.add(Enemy(context, width, height))
        }
        boom = Boom(context, width, height)


        // 3. Initialize SoundPool and load the sound
        val audioAttributes = AudioAttributes.Builder()

            .build()
        soundPool = SoundPool.Builder()
            .setMaxStreams(5) // Allow up to 5 sounds to play simultaneously
            .setAudioAttributes(audioAttributes)
            .build()
        // Make sure you have a sound file named 'explosion.wav' (or similar) in res/raw
        soundIdExplosion = soundPool.load(context, R.raw.shoot, 1)
    }

    constructor(context: Context?,width: Int, height: Int) : super(context){
        init(context!!, width, height)
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
        boom.x = -300
        boom.y = -300
        player.update()
        for (star in stars){
            star.update(player.speed)
        }
        for (enemy in enemies){
            enemy.update(player.speed)
            if (Rect.intersects(enemy.collisionBox, player.collisionBox)) {

                boom.x = enemy.x
                boom.y = enemy.y

                enemy.x = -300

                // 4. Play the sound on collision
                soundPool.play(soundIdExplosion, 1f, 1f, 0, 0, 1f)

            }
        }

    }

    fun draw(){
        if (surfaceHolder.surface.isValid) {
            canvas = surfaceHolder.lockCanvas()

            canvas.drawColor(Color.BLACK)

            paint.color = Color.YELLOW

            for (star in stars){
                paint.strokeWidth =  star.starWidth.toFloat()
                canvas.drawPoint(
                    star.x.toFloat(),
                    star.y.toFloat(),
                    paint)
            }


            //paint.style = Paint.Style.STROKE
            //paint.color = Color.GREEN
            //paint.strokeWidth = 3f

            for (enemy in enemies){
                canvas.drawBitmap(enemy.bitmap,
                    enemy.x.toFloat(),
                    enemy.y.toFloat(),
                    paint)
                //canvas.drawRect(enemy.collisionBox, paint)
            }

            canvas.drawBitmap(player.bitmap,
                player.x.toFloat(),
                player.y.toFloat(),
                paint)
            //canvas.drawRect(player.collisionBox, paint)


            canvas.drawBitmap(
                boom.bitmap,
                boom.x.toFloat(),
                boom.y.toFloat(), paint)


            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun control(){
        Thread.sleep(17)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                player.isBoosting = true
            }
            MotionEvent.ACTION_UP -> {
                player.isBoosting = false
            }
        }

        return  true
    }


}