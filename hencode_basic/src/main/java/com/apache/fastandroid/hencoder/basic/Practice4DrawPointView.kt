package com.apache.fastandroid.hencoder.basic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.kt.dp

/**
 * Created by Jerry on 2020/11/11.
 */
class Practice4DrawPointView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        strokeWidth = 20.dp
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.strokeCap = Paint.Cap.ROUND
        canvas.drawPoint(width / 4f, height / 2f, mPaint)

        mPaint.strokeCap = Paint.Cap.SQUARE
        canvas.drawPoint(width * 3 / 4f, height / 2f, mPaint)

    }
}