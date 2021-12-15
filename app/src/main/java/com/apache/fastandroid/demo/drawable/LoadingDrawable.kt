package com.apache.fastandroid.demo.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import com.apache.fastandroid.R
import com.blankj.utilcode.util.ColorUtils

/**
 * Created by Jerry on 2021/6/25.
 */
class LoadingDrawable(private val bitmap: Bitmap, val roundCorner: Int) : Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = ColorUtils.getColor(R.color.color_33FF8610)
    }
    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mBounds: RectF = RectF()

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        mBounds.set(bounds)
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(mBounds!!, roundCorner.toFloat(), roundCorner.toFloat(), paint)
        canvas.drawBitmap(
            bitmap,
            (intrinsicWidth / 2 - bitmap.width / 2).toFloat(),
            (intrinsicHeight / 2 - bitmap.height / 2).toFloat(),
            bitmapPaint
        )
    }

    override fun getIntrinsicWidth(): Int {
        return mBounds.width().toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return mBounds.height().toInt()
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }


}