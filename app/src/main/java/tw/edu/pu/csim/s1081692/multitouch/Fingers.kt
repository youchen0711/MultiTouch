package tw.edu.pu.csim.s1081692.multitouch

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View

class Fingers(context: Context) : View(context),ScaleGestureDetector.OnScaleGestureListener {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    //var xPos:Float = 200f
    //var yPos:Float = 200f
    var Count:Int = 0;
    var xPos = FloatArray(20)
    var yPos = FloatArray(20)
    var rainbow = IntArray(7)

    lateinit var bitmap: Bitmap

    lateinit var sg: ScaleGestureDetector
    var factor: Float = 1.0f

    init {
        rainbow = context.getResources().getIntArray(R.array.rainbow)
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.robot)
        sg = ScaleGestureDetector(context, this)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.LTGRAY)
        paint.color = Color.YELLOW
        for (i in 0..Count-1) {
            paint.color = rainbow[i % 7]
            canvas.drawCircle(xPos[i], yPos[i], 80f, paint)
        }
        paint.color = Color.BLUE
        paint.textSize = 50f
        canvas.drawText("多指觸控，圓形呈現彩虹顏色！", 50f,200f, paint)

        var SrcRect:Rect = Rect(0, 0, bitmap.width, bitmap.height) //裁切(顯示全部)
        var w:Int = (bitmap.width*factor/4).toInt()
        var h:Int = (bitmap.height*factor/4).toInt()
        var DestRect:Rect = Rect(200, 300, w+200, h+300)  //原圖較大，縮成1/4顯示
        canvas.drawBitmap(bitmap, SrcRect, DestRect, paint)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean{
        //xPos = event.getX()
        //yPos = event.getY()
        Count = event.getPointerCount()
        for (i in 0..Count-1) {
            xPos[i] = event.getX(i)
            yPos[i] = event.getY(i)
        }

        invalidate()
        sg.onTouchEvent(event)
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {

    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        factor += sg.getScaleFactor()-1
        return true
    }

}