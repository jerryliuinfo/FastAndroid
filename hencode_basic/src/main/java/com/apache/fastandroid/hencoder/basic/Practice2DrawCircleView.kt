package com.apache.fastandroid.hencoder.basic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.common.util.kt.dp

/**
 * Created by Jerry on 2020/11/11.
 */
class Practice2DrawCircleView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    companion object{
        private val RADIUS = 50.dp
        private val MARGIN = 20.dp
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint.reset()

        val leftTopCenterX = width / 2 - MARGIN - RADIUS
        val leftTopCenterY = height / 2 - MARGIN - RADIUS

        mPaint.apply {
            style = Paint.Style.FILL
            color = Color.BLACK
        }
        //左上圆
        canvas.drawCircle(leftTopCenterX,leftTopCenterY, RADIUS, mPaint)


        //右上圆
        mPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 2.dp
        }
        val rightTopCenterX = width / 2 + MARGIN + RADIUS
        val rightTopCenterY = height / 2 - MARGIN - RADIUS
        canvas.drawCircle(rightTopCenterX,rightTopCenterY, RADIUS, mPaint)


        //左下圆
        mPaint.apply {
            style = Paint.Style.FILL
            color = Color.BLUE
        }
        val leftBottomCenterX = width / 2 - MARGIN - RADIUS
        val leftBottomCenterY = height / 2 + MARGIN + RADIUS
        canvas.drawCircle(leftBottomCenterX,leftBottomCenterY, RADIUS, mPaint)

        //右下圆
        mPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 10.dp
        }
        val rightBottomCenterX = width / 2 + MARGIN + RADIUS
        val rightBottomCenterY = height / 2 + MARGIN + RADIUS
        canvas.drawCircle(rightBottomCenterX,rightBottomCenterY, RADIUS, mPaint)

    }
}