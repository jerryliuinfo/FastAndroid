package com.tesla.framework.ui.widget.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.view.View

/**
 * Created by Jerry on 2020/11/13.
 */
class CircleImageDrawable(val bitmap: Bitmap) : Drawable() {
    private var mWidth:Int = 0

    init {
        mWidth = bitmap.width.coerceAtMost(bitmap.height)
    }

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(mWidth / 2 .toFloat(), mWidth / 2 .toFloat(), mWidth / 2.toFloat(), mPaint)
    }

    override fun getIntrinsicWidth(): Int {
        return mWidth
    }

    override fun getIntrinsicHeight(): Int {
        return mWidth
    }

    override fun setAlpha(alpha: Int) {
       mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT;
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }
}