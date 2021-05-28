package com.hencoder.hencoderpracticedraw2.practice

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.common.util.kt.sp

/**
 * Created by Jerry on 2020/12/14.
 */
class Practice12PathEffectView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    /**
     * 把所有拐角变成圆角。
     */
    private val mCornerPathEffect = CornerPathEffect(20f)

    /**
     * 把线条进行随机的偏离，让轮廓变得乱七八糟。乱七八糟的方式和程度由参数决定。
     * 具体的做法是，把绘制改为使用定长的线段来拼接，并且在拼接的时候对路径进行随机偏离。
     * 它的构造方法 DiscretePathEffect(float segmentLength, float deviation) 的两个参数中，
     * segmentLength 是用来拼接的每个线段的长度， deviation 是偏离量。这两个值设置得不一样，显示效果也会不一样
     */
    private val mDiscretePathEffect = DiscretePathEffect(20f,10f)

    /**
     * 使用虚线来绘制线条。它的构造方法 DashPathEffect(float[] intervals, float phase) 中， 第一个参数 intervals 是一个数组，
     * 它指定了虚线的格式：数组中元素必须为偶数（最少是 2 个），按照「画线长度、空白长度、画线长度、空白长度」……的顺序排列，
     * 例如上面代码中的 20, 5, 10, 5 就表示虚线是按照「画 20 像素、空 5 像素、画 10 像素、空 5 像素」的模式来绘制；第二个参数 phase 是虚线的偏移量。
     */
    private val mDashPathEffect = DashPathEffect(floatArrayOf(20f, 10f, 5f, 10f),0f)


    /**
     * 这个方法比 DashPathEffect 多一个前缀 Path ，所以顾名思义，它是使用一个 Path 来绘制「虚线」
     *
     *  shape 参数是用来绘制的 Path ； advance 是两个相邻的 shape 段之间的间隔，不过注意，这个间隔是两个 shape 段的起点的间隔，
     *  而不是前一个的终点和后一个的起点的距离； phase 和 DashPathEffect 中一样，是虚线的偏移；最后一个参数 style，是用来指定拐弯改变的时候
     *  shape 的转换方式。style 的类型为 PathDashPathEffect.Style ，是一个 enum ，具体有三个值：
        TRANSLATE：位移
        ROTATE：旋转
        MORPH：变体
     */
    private val dashPath = Path().apply {
        lineTo(20f, -30f)
        lineTo(40f, 0f)
        close()
    }
    private val mPathDashPathEffect = PathDashPathEffect(dashPath,40f,0f,PathDashPathEffect.Style.TRANSLATE)


    /**
     * 这是一个组合效果类的 PathEffect 。它的行为特别简单，就是分别按照两种 PathEffect 分别对目标进行绘制。
     */
    private val firstPath = DashPathEffect(floatArrayOf(20f,10f),0f)
    private val secondPath = DiscretePathEffect(20f,0f)
    private val mSumPathEffect = SumPathEffect(firstPath,secondPath)

    /**
     *这也是一个组合效果类的 PathEffect 。不过它是先对目标 Path 使用一个 PathEffect，然后再对这个改变后的 Path 使用另一个 PathEffect。


     */
    private val mComposePathEffect = ComposePathEffect(mDashPathEffect, mDiscretePathEffect)



    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val mTextPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 14.sp
    }

    private val mPath = Path().apply {
        moveTo(50f, 100f)
        rLineTo(50f, 100f)
        rLineTo(80f, -150f)
        rLineTo(100f, 100f)
        rLineTo(70f, -120f)
        rLineTo(150f, 80f)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val offsetY = 220f

        mPaint.pathEffect = mCornerPathEffect
        canvas.drawPath(mPath,mPaint)
        canvas.drawText("CornerPath" ,100f,offsetY, mTextPaint )


        canvas.save()
        canvas.translate(500f,0f)
        mPaint.pathEffect = mDiscretePathEffect
        canvas.drawPath(mPath,mPaint)
        canvas.drawText("DiscretePathEffect" ,100f,offsetY, mTextPaint )
        canvas.restore()




        canvas.save()
        canvas.translate(0f,210f)
        mPaint.pathEffect = mDashPathEffect
        canvas.drawPath(mPath,mPaint)
        canvas.drawText("DashPathEffect" ,100f,offsetY, mTextPaint )

        canvas.translate(500f,0f)
        mPaint.pathEffect = mPathDashPathEffect
        canvas.drawPath(mPath,mPaint)
        canvas.drawText("PathDashPathEffect" ,100f,offsetY, mTextPaint )
        canvas.restore()



        canvas.save()
        canvas.translate(0f,420f)
        mPaint.pathEffect = mSumPathEffect
        canvas.drawPath(mPath,mPaint)
        canvas.drawText("SumPathEffect" ,100f,offsetY, mTextPaint )


        canvas.translate(500f,0f)
        mPaint.pathEffect = mComposePathEffect
        canvas.drawPath(mPath,mPaint)
        canvas.drawText("ComposePathEffect" ,100f,offsetY, mTextPaint )
        canvas.restore()


    }

}