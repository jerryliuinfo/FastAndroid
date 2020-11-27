package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.hencoder.paint.R

/**
 * Created by Jerry on 2020/11/19.
 */
class Practice06LightingColorFilterView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.batman)
    var colorFilter1: ColorFilter = LightingColorFilter(0x00ffff, 0x000000)
    var colorFilter2: ColorFilter = LightingColorFilter(0xffffff, 0x003000)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.colorFilter = colorFilter1
        canvas.drawBitmap(mBitmap,0f,0f,mPaint)

        mPaint.colorFilter = colorFilter2
        canvas.drawBitmap(mBitmap,mBitmap.width.toFloat() + 100,0f,mPaint)
    }
}