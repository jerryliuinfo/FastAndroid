package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/12/16.
 */
class Practice12MeasureTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaint1:Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
    }
    private val mPaint2:Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 90f
        color = Color.RED
    }

    private val mLinePaint:Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 20f
        color = Color.RED
    }
    private var text1MeasuredLength = 0f
    private var text2MeasuredLength = 0f


    var text1 = "三个月内你胖了"
    var text2 = "4.5"
    var text3 = "公斤"
    private val offsetX = 200f
    private val offsetY = 200f

    init {
        text1MeasuredLength = mPaint1.measureText(text1)
        text2MeasuredLength = mPaint2.measureText(text2)
    }

    private val totalTextWidth = text1MeasuredLength + text2MeasuredLength + mPaint1.measureText(text3)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawText(text1, offsetX,offsetY,mPaint1)
        canvas.drawText(text2, offsetX + text1MeasuredLength ,offsetY,mPaint2)
        canvas.drawText(text3, offsetX + text1MeasuredLength +text2MeasuredLength,offsetY,mPaint1)
        canvas.drawLine(offsetX,offsetY+mLinePaint.strokeWidth, offsetX+totalTextWidth, offsetY + mLinePaint.strokeWidth,mLinePaint)
    }
}