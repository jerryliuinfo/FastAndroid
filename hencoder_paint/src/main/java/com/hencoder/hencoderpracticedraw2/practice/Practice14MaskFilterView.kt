package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.hencoder.paint.R
import com.tesla.framework.common.util.kt.dp
import com.tesla.framework.common.util.kt.textCenterX

/**
 * Created by Jerry on 2020/12/14.
 */
class Practice14MaskFilterView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    /**
     * 在绘制层上方附加效果。
     * BlurMaskFilter: 模糊效果的 MaskFilter。
     */
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 14f.dp
        color = Color.RED
    }

    private val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.what_the_fuck)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.translate(100f,50f)
        mPaint.maskFilter = BlurMaskFilter(50f,BlurMaskFilter.Blur.NORMAL)
        canvas.drawBitmap(mBitmap,0f,0f,mPaint)
        var content = "Normal"
        canvas.drawText(content, mTextPaint.textCenterX(content, (mBitmap.width / 2).toFloat()), mBitmap.height.toFloat(), mTextPaint)
        canvas.restore()



        canvas.save()
        canvas.translate((mBitmap.width + 300).toFloat(),50f)

        mPaint.maskFilter = BlurMaskFilter(50f,BlurMaskFilter.Blur.SOLID)
        canvas.drawBitmap(mBitmap,0f,0f,mPaint)
        content = "Solid"
        canvas.drawText(content, mTextPaint.textCenterX(content, (mBitmap.width / 2).toFloat()), mBitmap.height.toFloat(), mTextPaint)

        canvas.restore()


        canvas.save()
        canvas.translate(100f,mBitmap.height + 100f)

        mPaint.maskFilter = BlurMaskFilter(50f,BlurMaskFilter.Blur.OUTER)
        canvas.drawBitmap(mBitmap,0f, 0f,mPaint)
        content = "OUTER"
        canvas.drawText(content, mTextPaint.textCenterX(content, (mBitmap.width / 2).toFloat()), mBitmap.height.toFloat(), mTextPaint)
        canvas.restore()




        canvas.save()
        canvas.translate((mBitmap.width + 300).toFloat(), (mBitmap.height + 100).toFloat())
        mPaint.maskFilter = BlurMaskFilter(50f,BlurMaskFilter.Blur.INNER)
        canvas.drawBitmap(mBitmap,0f,0f,mPaint)
        content = "INNER"
        canvas.drawText(content, mTextPaint.textCenterX(content, (mBitmap.width / 2).toFloat()), mBitmap.height.toFloat(), mTextPaint)
        canvas.restore()
    }
}