package com.hencoder.hencoderpracticedraw4.practice

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.hencoder.hencoderpracticedraw4.R

/**
 * Created by Jerry on 2020/12/17.
 */
class Practice02ClipPathView(context: Context?, attrs: AttributeSet) : View(context, attrs, 0) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.STROKE
    }

    private val mRectPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.STROKE
    }

    private val mBitmap = BitmapFactory.decodeResource(resources, R.drawable.maps)

    private val mPath1 = Path()
    private val mPath2 = Path()


    private val mPoint1 = Point(200,200)

    private val mPoint2 = Point(600,200)

    init {
        mPath1.addCircle((mPoint1.x + 200).toFloat(), (mPoint1.y+ 200).toFloat(), 150.toFloat(),Path.Direction.CW)

        mPath2.setFillType(Path.FillType.INVERSE_WINDING)
        mPath2.addCircle((mPoint2.x + 200).toFloat(), (mPoint2.y+ 200).toFloat(), 150.toFloat(),Path.Direction.CW)


    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawPath(mPath1,mPaint)

        canvas.drawRect(mPoint1.x.toFloat(), mPoint1.y.toFloat(), (mPoint1.x + mBitmap.width).toFloat(), (mPoint1.y + mBitmap.height).toFloat(),mRectPaint)

        canvas.save()
        canvas.clipPath(mPath1)
        canvas.drawBitmap(mBitmap,mPoint1.x.toFloat(), mPoint1.y.toFloat(), mPaint)
        canvas.restore()

        canvas.drawPath(mPath2,mPaint)
        canvas.drawRect(mPoint2.x.toFloat(), mPoint2.y.toFloat(), (mPoint2.x + mBitmap.width).toFloat(), (mPoint2.y + mBitmap.height).toFloat(),mRectPaint)

        canvas.save()
        canvas.clipPath(mPath2)
        canvas.drawBitmap(mBitmap,mPoint2.x.toFloat(), mPoint2.y.toFloat(), mPaint)
        canvas.restore()

    }
}