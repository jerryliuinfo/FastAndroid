package com.hencoder.hencoderpracticedraw4.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.hencoderpracticedraw4.R

/**
 * Created by Jerry on 2020/12/17.
 */
class Practice01ClipRectView@JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)  {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.RED
    }

    private val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.maps)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val left: Int = (width - mBitmap.getWidth()) / 2
        val top: Int = (height - mBitmap.getHeight()) / 2

        canvas.drawRect(left.toFloat(), top.toFloat(), (left + mBitmap.width).toFloat(), (top+mBitmap.height).toFloat(), mRectPaint)

        canvas.save()
        //绘制内容将限制在这个区域
        canvas.clipRect(left + 50, top + 50, left + 300, top + 200)
        canvas.drawBitmap(mBitmap, left.toFloat(), top.toFloat(), mPaint)
        canvas.restore()


    }
}