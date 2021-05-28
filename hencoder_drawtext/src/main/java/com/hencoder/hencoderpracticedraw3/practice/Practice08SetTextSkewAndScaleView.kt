package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.common.util.kt.textCenterY

/**
 * Created by Jerry on 2020/12/14.
 * 设置文字横向错切角度。其实就是文字倾斜度的啦
 */
class Practice08SetTextSkewAndScaleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    companion object{
        private const val TEXT = "Hello HenCoder"
    }

    private val mPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.textSkewX = -0.5f
        mPaint.textScaleX = 1f
        canvas.drawText(TEXT, 100f,100f, mPaint)

        mPaint.textSkewX = 0f
        mPaint.textScaleX = 0.6f
        canvas.drawText(TEXT, 100f,100f + mPaint.textCenterY(TEXT,100f), mPaint)

        mPaint.textSkewX = 0f
        mPaint.textScaleX = 1.6f
        canvas.drawText(TEXT, 100f,100f + mPaint.textCenterY(TEXT,200f), mPaint)
    }
}