package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/12/14.
 */
class Practice06SetStrikeThruTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val mPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.isStrikeThruText = true
        mPaint.isUnderlineText = false
        canvas.drawText("Hello HenCoder", 100f,100f, mPaint)

        mPaint.isStrikeThruText = false
        mPaint.isUnderlineText = true
        canvas.drawText("Hello HenCoder", 100f,200f, mPaint)
    }
}