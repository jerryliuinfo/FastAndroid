package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.common.util.log.FastLog

/**
 * Created by Jerry on 2020/12/16.
 */
class Practice14GetFontMetricsView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    companion object{
        private const val TAG = "Practice14GetFontMetricsView"
    }
    private val mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 20f
        color = Color.parseColor("#E91E63")
    }

    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 160f
    }

    private val fontMetrics = mTextPaint.fontMetrics
    private val yOffset = -(fontMetrics.ascent + fontMetrics.descent) / 2
    private val mTexts = listOf("A", "a", "J", "j", "Â", "â")

    private val mTop = 200f
    private val mBottom = 400f
    private val mMiddle = (mTop + mBottom) / 2

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(50f, mTop, (width - 50).toFloat(),mBottom,mRectPaint)
        FastLog.d(TAG, "offset: ${yOffset}, top:${fontMetrics.top}, ascent: ${fontMetrics.ascent}, descent:${fontMetrics.descent}, botton: ${fontMetrics.bottom},leading:${fontMetrics.leading}")
        canvas.drawText(mTexts[0],100f, mMiddle + yOffset, mTextPaint)
        canvas.drawText(mTexts[1],200f, mMiddle  + yOffset, mTextPaint)
        canvas.drawText(mTexts[2],300f, mMiddle  + yOffset, mTextPaint)
        canvas.drawText(mTexts[3],400f, mMiddle + yOffset, mTextPaint)
        canvas.drawText(mTexts[4],400f, mMiddle + yOffset, mTextPaint)
        canvas.drawText(mTexts[5],500f, mMiddle + yOffset, mTextPaint)
    }
}