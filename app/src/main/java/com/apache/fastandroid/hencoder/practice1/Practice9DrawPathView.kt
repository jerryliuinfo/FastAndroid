package com.apache.fastandroid.hencoder.practice1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/11/12.
 */
class Practice9DrawPathView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPath = Path()

    private val mRect = RectF()

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPath.reset()
        mPaint.reset()

        mRect.set(200f,200f,400f,400f)
        mPath.addArc(mRect, -225f,225f)
        mPaint.apply {
            color = Color.BLACK
        }
        canvas.drawPath(mPath, mPaint)

        mRect.set(400f,200f,600f,400f)
        mPaint.apply {
            color = Color.GREEN
        }
        mPath.arcTo(mRect, -180f,225f)

        mPath.lineTo(400f,542f)
        canvas.drawPath(mPath, mPaint)
    }
}