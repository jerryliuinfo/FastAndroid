package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.hencoder.paint.R

/**
 * Created by Jerry on 2020/11/13.
 */
class Practice04BitmapShaderView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.batman)


    private var mWidth:Int = 0

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        shader = BitmapShader(mBitmap, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP)
    }

    init {
        mWidth = Math.min(mBitmap.width, mBitmap.height)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

       /* canvas.save()

        mMatrix.postTranslate(width / 2 .toFloat(),  height / 2.toFloat())

        canvas.setMatrix(mMatrix)
        canvas.drawCircle(width / 2 .toFloat(),  height / 2.toFloat(), mBitmap.width / 2.toFloat(), mPaint)

        canvas.restore()*/


        canvas.drawCircle(mWidth / 2 .toFloat(),  mWidth / 2.toFloat(), mWidth.toFloat() / 2, mPaint)

    }
}