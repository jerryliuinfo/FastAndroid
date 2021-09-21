package com.hencoder.hencoderpracticedraw4.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.hencoderpracticedraw4.R

/**
 * Created by Jerry on 2021/8/25.
 */
class Practice04ScaleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val mRectPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
    }

    private val mPoint1 = Point(200,200)
    private val point2 = Point(600,200)

    private val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.maps)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.scale(1.3f,1.3f, ((mPoint1.x + mBitmap.width) / 2).toFloat(), ((mPoint1.y + mBitmap.height) /2).toFloat())
        canvas.drawBitmap(mBitmap,mPoint1.x.toFloat(),mPoint1.y.toFloat(),mRectPaint)
        canvas.restore()

        canvas.save()
        canvas.scale(
            0.6f,
            1.6f,
            (point2.x + mBitmap.width / 2).toFloat(),
            (point2.y + mBitmap.height / 2).toFloat()
        )
        canvas.drawBitmap(mBitmap, point2.x.toFloat(), point2.y.toFloat(), mRectPaint)
        canvas.restore()



    }
}