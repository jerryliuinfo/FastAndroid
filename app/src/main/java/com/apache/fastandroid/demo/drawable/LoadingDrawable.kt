package com.apache.fastandroid.demo.drawable

import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.graphics.toRectF
import com.apache.fastandroid.R
import com.blankj.utilcode.util.ColorUtils

/**
 * Created by Jerry on 2021/6/25.
 */
class LoadingDrawable(private val bitmap: Bitmap, val roundCorner: Int) : BaseDrawable() {

    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    init {

        paint.color =  ColorUtils.getColor(R.color.color_33FF8610)
    }



    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(bounds.toRectF(), roundCorner.toFloat(), roundCorner.toFloat(), paint)
        canvas.drawBitmap(
            bitmap,
            (intrinsicWidth / 2 - bitmap.width / 2).toFloat(),
            (intrinsicHeight / 2 - bitmap.height / 2).toFloat(),
            bitmapPaint
        )
    }




}