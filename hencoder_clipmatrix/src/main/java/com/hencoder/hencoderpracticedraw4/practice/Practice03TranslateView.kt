package com.hencoder.hencoderpracticedraw4.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.hencoderpracticedraw4.R

/**
 * Created by Jerry on 2021/8/25.
 */
class Practice03TranslateView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mRectPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
    }

    private val mPoint1 = Point(200,150)
    private val mPoint2 = Point(600,150)

    private val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.maps)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRect(mPoint1.x.toFloat(), mPoint1.y.toFloat(), (mPoint1.x+ mBitmap.width).toFloat(), mPoint1.y+ mBitmap.height.toFloat(),mRectPaint)
        canvas.save()
        canvas.translate(-100f,-100f)
        canvas.drawBitmap(mBitmap,mPoint1.x.toFloat(),mPoint1.y.toFloat(),mRectPaint)
        canvas.restore()


        canvas.drawRect(mPoint2.x.toFloat(), mPoint2.y.toFloat(), (mPoint2.x+ mBitmap.width).toFloat(), mPoint2.y+ mBitmap.height.toFloat(),mRectPaint)
        canvas.save()
        canvas.translate(200f,0f)
        canvas.drawBitmap(mBitmap,mPoint2.x.toFloat(),mPoint2.y.toFloat(),mRectPaint)
        canvas.restore()

    }
}