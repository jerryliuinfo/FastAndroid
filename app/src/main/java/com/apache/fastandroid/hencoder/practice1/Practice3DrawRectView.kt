package com.apache.fastandroid.hencoder.practice1

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.hencoder.animation.dp

/**
 * Created by Jerry on 2020/11/11.
 */
class Practice3DrawRectView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    companion object {
        private val mRect = RectF()
        private val RADIUS = 50.dp
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRect.set(w / 2 - RADIUS, height / 2 - RADIUS, w / 2 + RADIUS, height / 2 + RADIUS)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRect(mRect, mPaint)
    }
}