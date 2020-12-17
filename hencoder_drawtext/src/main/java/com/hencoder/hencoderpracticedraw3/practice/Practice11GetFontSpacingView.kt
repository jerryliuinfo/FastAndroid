package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/12/14.
 */
class Practice11GetFontSpacingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
        textAlign = Paint.Align.CENTER
    }

    private val TEXT = "Hello HenCoder"

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(TEXT, width / 2.toFloat(),150f,mPaint)
        canvas.drawText(TEXT, width / 2.toFloat(),150f + mPaint.fontSpacing, mPaint)
        canvas.drawText(TEXT, width / 2.toFloat(),150f + mPaint.fontSpacing * 2,mPaint)
    }
}