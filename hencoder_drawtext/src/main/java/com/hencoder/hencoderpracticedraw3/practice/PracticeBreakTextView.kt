package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/12/16.
 */
class PracticeBreakTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val TEXT = "Hello HenCoder"

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)


    private val measuredWidth = floatArrayOf()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val measuredCount = mPaint.breakText(TEXT, 0, TEXT.length, true, 300f, measuredWidth)
        canvas.drawText(TEXT,0f, measuredCount.toFloat(), mPaint)

    }
}