package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/11/19.
 */
class Practice09StrokeCapView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 40f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)



        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawLine(50f,50f, 400f, 50f,mPaint)

        mPaint.strokeCap = Paint.Cap.BUTT
        canvas.drawLine(50f,150f, 400f, 150f,mPaint)

        mPaint.strokeCap = Paint.Cap.SQUARE
        canvas.drawLine(50f,250f, 400f, 250f,mPaint)

    }
}