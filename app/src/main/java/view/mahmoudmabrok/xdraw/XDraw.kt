package view.mahmoudmabrok.xdraw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import kotlin.math.abs

class XDraw(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var mWidth = 100f
    var mHeight = 100f
    var mCircleX = 10f
    var mCircleY = 10f
    var paint: Paint = Paint().apply {
        color = Color.GREEN
        isAntiAlias = true
        isDither = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        strokeWidth = 12f
    }

    private var motionX = 0f
    private var motionY = 0f
    private var currentX = 0f
    private var currentY = 0f
    private val path = Path()
    private var touchToloreance = ViewConfiguration.get(context).scaledTouchSlop

    private lateinit var extraBitmap: Bitmap
    private lateinit var extraCanvas: Canvas

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        if (::extraBitmap.isInitialized) extraBitmap.recycle()
        // ARGB_8888 -> each color with 1 byte
        extraBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        motionX = event.x
        motionY = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> startAction()
            MotionEvent.ACTION_MOVE -> moveAction()
            MotionEvent.ACTION_UP -> endAction()
        }
        return true
    }

    private fun endAction() {
        path.reset()
    }

    private fun startAction() {
        path.reset()
        path.moveTo(motionX, motionY)
        currentX = motionX
        currentY = motionY
    }

    private fun moveAction() {
        val dx = abs(motionX - currentX)
        val dy = abs(motionY - currentY)

        // make sure it ahs significant amount of move
        if (dx >= touchToloreance || dy >= touchToloreance) {
            // path.quadTo(,,)
            path.quadTo(
                currentX, currentY,
                (currentX + motionX) / 2, (currentY + motionY) / 2
            )
            //  path.lineTo(motionX,motionY)
            currentX = motionX
            currentY = motionY
            changePaint()
            extraCanvas.drawPath(path, paint)
        }

        invalidate()
    }

    private fun changePaint() {
        val r = java.util.Random().nextInt(3)
        paint.color = when (r) {
            0 -> Color.RED
            1 -> Color.GREEN
            2 -> Color.BLUE
            else -> Color.BLACK
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(extraBitmap, 0f, 0f, null)
        Log.d("XDraw", "onDraw")
    }
}