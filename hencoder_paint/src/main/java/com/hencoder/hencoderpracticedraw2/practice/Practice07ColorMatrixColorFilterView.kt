package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.hencoder.paint.R

/**
 * Created by Jerry on 2020/11/19.
 */
class Practice07ColorMatrixColorFilterView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val mBitmap = BitmapFactory.decodeResource(resources,R.drawable.batman)

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        val colorMatrix = ColorMatrix().apply {
            setSaturation(1f)
        }
        colorFilter = ColorMatrixColorFilter(colorMatrix)
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(mBitmap,width / 2.toFloat(),height /2 .toFloat(),mPaint)
    }
}