package com.tesla.framework.ui.widget.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.view.View
import kotlin.properties.Delegates

/**
 * Created by Jerry on 2020/11/13.
 */
class RoundImageDrawable(val bitmap: Bitmap) : Drawable() {
    private var mWidth:Int = 0

    init {
        mWidth = bitmap.width.coerceAtMost(bitmap.height)
    }

    private val mRect = RectF()

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = BitmapShader(bitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
    }

    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 20f
        color = Color.RED
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(mRect, 30f,30f, mPaint)
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)

        mRect.set(left.toFloat(),top.toFloat(),right.toFloat(),bottom.toFloat())
    }

    override fun getIntrinsicWidth(): Int {
        return bitmap.width
    }

    override fun getIntrinsicHeight(): Int {
        return bitmap.height
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