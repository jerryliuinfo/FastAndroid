package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.kt.dp


/**
 * Created by Jerry on 2020/11/12.
 */
class Practice01LinearGradientView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = LinearGradient(100.toFloat(), 100.toFloat(), 500.toFloat(), 500.toFloat(), Color.parseColor("#E91E63"),
                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(width / 2.toFloat(), height / 2 .toFloat(), 100f.dp, mPaint )

    }
}