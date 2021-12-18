package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.kt.textCenterX

/**
 * Created by Jerry on 2020/12/14.
 */
class Practice16TextPathView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100f
        style = Paint.Style.STROKE
    }

    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100f
    }

    private val mPath = Path()

    private val text = "Hello HenCoder"

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawText(text, mPaint.textCenterX(text, (width/2).toFloat()), (height / 2).toFloat(), mPaint)

        canvas.save()
        canvas.translate(mPaint.textCenterX(text, (width/2).toFloat()),100f)
        mPaint.getTextPath(text, 0, text.length,50f,100f,mPath)
        canvas.drawPath(mPath, mTextPaint)
        canvas.restore()
    }
}