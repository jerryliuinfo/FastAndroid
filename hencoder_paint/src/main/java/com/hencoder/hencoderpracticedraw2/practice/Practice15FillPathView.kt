package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/12/14.
 */
class Practice15FillPathView(context: Context?, attrs: AttributeSet?) : View(context, attrs){

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val mPath1 = Path()
    private val mPath2 = Path()
    private val mPath3 = Path()

    private val mPath = Path().apply {
        moveTo(50f,100f)
        rLineTo(50f, 100f)
        rLineTo(80f, -150f)
        rLineTo(100f, 100f)
        rLineTo(70f, -120f)
        rLineTo(150f, 80f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.strokeWidth = 0f
        canvas.drawPath(mPath,mPaint)

        canvas.save()
        canvas.translate(500f,0f)
        mPaint.getFillPath(mPath,mPath1)
        canvas.drawPath(mPath1,mPaint)

        canvas.restore()




        canvas.save()
        canvas.translate(0f, 200f)
        mPaint.style = Paint.Style.STROKE
        canvas.drawPath(mPath, mPaint)
        canvas.restore()

        canvas.save()
        canvas.translate(500f, 200f)
        mPaint.getFillPath(mPath,mPath2)
        canvas.drawPath(mPath2, mPaint)
        canvas.restore()
    }
}