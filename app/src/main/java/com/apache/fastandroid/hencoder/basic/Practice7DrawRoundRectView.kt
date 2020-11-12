package com.apache.fastandroid.hencoder.basic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.hencoder.animation.dp

/**
 * Created by Jerry on 2020/11/12.
 */
class Practice7DrawRoundRectView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    private val mRect = RectF()

    companion object{
        private val HORIZONTAL_RADIUS = 60.dp
        private val VERTICAL_RADIUS = 30.dp
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mRect.set(width / 2 - HORIZONTAL_RADIUS, height / 2 - VERTICAL_RADIUS, width / 2 + HORIZONTAL_RADIUS, height / 2 + VERTICAL_RADIUS)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawRoundRect(mRect,20f,40f,mPaint)
    }
}