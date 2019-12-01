package view.mahmoudmabrok.xdraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class XDraw(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var mWidth = 100f
    var mHeight = 100f
    var mCircleX = 10f
    var mCircleY = 10f
    var paint: Paint = Paint()

    init {
        paint = paint.apply {
            color = 0xffaaff
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w.toFloat()
        mHeight = h.toFloat()
        mCircleX = mWidth / 2
        mCircleY = mHeight / 2
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, mWidth, mHeight, paint)
        canvas?.drawCircle(mCircleX, mCircleY, 50f, paint)
    }
}