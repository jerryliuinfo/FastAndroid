package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.hencoder.paint.R
import com.tesla.framework.common.util.ResUtil


/**
 * Created by Jerry on 2020/11/12.
 */
class Practice03SweepGradientView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr){


    private var mRotate:Int = 0
    private var mShader: Shader? = null
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mMatrix = Matrix()

    companion object{
        private const val TAG = "Practice03SweepGradientView"
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mShader = SweepGradient(width / 2.toFloat(), height / 2 .toFloat(), ResUtil.getColor(R.color.sweep_start_color),
                ResUtil.getColor(R.color.sweep_end_color))
        mPaint.shader = mShader
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mMatrix.setRotate(mRotate.toFloat(), width / 2.toFloat(), height / 2.toFloat())
        mShader?.setLocalMatrix(mMatrix)
        canvas.drawCircle(width / 2.toFloat(), height / 2 .toFloat(), 200f, mPaint )
    }

    private val mHander = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            mRotate += 30
            if (mRotate > 360){
                mRotate = 0
            }
            invalidate()
            sendEmptyMessageDelayed(100,500)
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        mHander.sendEmptyMessage(100)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHander.removeCallbacksAndMessages(null)
    }
}