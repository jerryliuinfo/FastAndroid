package com.hencoder.hencoderpracticedraw3.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/12/14.
 * 设置字体
 */
class Practice04SetTypefaceView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val mPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 60f
    }

    companion object{
        private const val TEXT = "Hello HenCoder"
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.typeface = Typeface.DEFAULT
        canvas.drawText(TEXT,100f,50f,mPaint)

        mPaint.typeface = Typeface.SERIF
        canvas.drawText(TEXT,100f,150f,mPaint)

        mPaint.typeface = Typeface.createFromAsset(resources.assets,"Satisfy-Regular.ttf")
        canvas.drawText(TEXT,100f,250f,mPaint)
    }
}