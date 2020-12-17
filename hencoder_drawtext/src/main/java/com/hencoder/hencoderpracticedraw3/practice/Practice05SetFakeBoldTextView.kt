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
class Practice05SetFakeBoldTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        isFakeBoldText = true
        textSize = 60f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawText("Hello HenCoder", 200f,200f,mPaint)
    }
}