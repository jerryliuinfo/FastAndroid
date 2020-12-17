package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.common.util.textCenterX

/**
 * Created by Jerry on 2020/12/14.
 * 设置文字的对齐方式。一共有三个值：LEFT CETNER 和 RIGHT。默认值为 LEFT。
 */
class Practice10SetTextAlignView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val TEXT = "Hello HenCoder"


    private val mPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.textAlign = Paint.Align.LEFT
        canvas.drawText(TEXT,width / 2.toFloat(),100f, mPaint)

        mPaint.textAlign = Paint.Align.CENTER
        canvas.drawText(TEXT,width / 2.toFloat(),200f, mPaint)

        mPaint.textAlign = Paint.Align.RIGHT
        canvas.drawText(TEXT,width / 2.toFloat(),300f, mPaint)

        mPaint.textAlign = Paint.Align.LEFT
        canvas.drawText(TEXT,mPaint.textCenterX(TEXT, (width / 2).toFloat()),400f, mPaint)

    }
}