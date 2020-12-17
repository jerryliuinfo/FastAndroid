package com.apache.fastandroid.hencoder.basic

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.common.util.dp

/**
 * Created by Jerry on 2020/11/12.
 */
class Practice8DrawArcView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLACK
    }

    private val mRect = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRect.set(200f, 100f, 800f, 500f)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        mPaint.reset()
        mPaint.apply {
                    style = Paint.Style.FILL
                    color = Color.BLACK
        }

        //style 为Fill 且 useCenter 为true，代表画扇形
        canvas.drawArc(mRect, -110f, 100f, true, mPaint)

        mPaint.apply {
            style = Paint.Style.FILL
            color = Color.BLACK
        }
        //style 为Fill 且 useCenter 为false，代表绘制封口的弧形
        canvas.drawArc(mRect, 20f, 140f, false, mPaint)

        mPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 1.dp
            color = Color.BLACK
        }
        //style 为STROKE 且 useCenter 为false，代表绘制不封口的弧形
        canvas.drawArc(mRect, 180f, 60f, false, mPaint)
    }
}