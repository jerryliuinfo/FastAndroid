package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.hencoder.paint.R

/**
 * Created by Jerry on 2020/11/19.
 */
class Practice08XfermodeView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBitmap1 = BitmapFactory.decodeResource(resources, R.drawable.batman)
    private val mBitmap2 = BitmapFactory.decodeResource(resources, R.drawable.batman_logo)

    private val xFerMode1 = PorterDuff.Mode.SRC
    private val xFerMode2 = PorterDuff.Mode.DST_IN
    private val xFerMode3 = PorterDuff.Mode.DST_OUT

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var saveCount = canvas.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)

        canvas.drawBitmap(mBitmap1,0f,0f,mPaint)

        mPaint.xfermode  = PorterDuffXfermode(xFerMode1)
        canvas.drawBitmap(mBitmap2,0f,0f,mPaint)
        mPaint.xfermode  = null


        canvas.drawBitmap(mBitmap1,mBitmap1.width + 100f,0f,mPaint)
        mPaint.xfermode  = PorterDuffXfermode(xFerMode2)
        canvas.drawBitmap(mBitmap2,mBitmap1.width + 100f,0f,mPaint)
        mPaint.xfermode = null


        canvas.drawBitmap(mBitmap1,0f,mBitmap1.height + 100f,mPaint)
        mPaint.xfermode  = PorterDuffXfermode(xFerMode3)
        canvas.drawBitmap(mBitmap2,0f,mBitmap1.height + 100f,mPaint)
        mPaint.xfermode = null

        canvas.restoreToCount(saveCount)

    }
}