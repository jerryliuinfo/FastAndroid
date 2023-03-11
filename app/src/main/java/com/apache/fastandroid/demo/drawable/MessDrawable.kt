package com.apache.fastandroid.demo.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import com.tesla.framework.kt.dp

private val INTERVAL = 50.dp
/**
 * Created by Jerry on 2021/12/14.
 */
class MessDrawable(): Drawable() {
    companion object{
        private const val TAG = "MessDrawable"
    }
    private val mPaint = Paint().apply {
        strokeWidth = 5.dp
        color = Color.RED
    }
    override fun draw(canvas: Canvas) {
        var x = bounds.left.toFloat()
        while (x <= bounds.right){
            canvas.drawLine(x,bounds.top.toFloat(),x,bounds.bottom.toFloat(),mPaint)
            x+= INTERVAL
        }
        var y = bounds.top.toFloat()
        while (y <= bounds.bottom){
            canvas.drawLine(bounds.left.toFloat(),y  ,bounds.right.toFloat(),y,mPaint)
            y+= INTERVAL
        }
    }

     var interval:Int = 0
        set(value) {
            field = value
        }


    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getAlpha(): Int {
        return mPaint.alpha

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
       when(mPaint.alpha){
           0 -> return PixelFormat.TRANSPARENT
           0xff -> return PixelFormat.OPAQUE
           else -> return PixelFormat.OPAQUE
       }
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
    }
}