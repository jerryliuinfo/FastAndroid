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
class Practice11PieChartView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val mRect = RectF()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRect.set(width / 2 - RADIUS, height / 2 - RADIUS, width / 2 + RADIUS, height / 2 + RADIUS)
    }

    companion object{
        private const val START_ANGEL = -45f
        private  var RADIUS = 100.dp
        private const val GAP_DEGRESS = 3
    }

    private val mDatas = arrayListOf(
            PieBean("Lollipop", 20f, "#FF0000"),
            PieBean("Java", 5f, "#00FF00"),
            PieBean("Kotlin", 5f, "#0000FF"),
            PieBean("Python", 25f, "#FF0000"),
            PieBean("JavasCRIPT", 23f, "#00FF00"),
            PieBean("C++", 22f, "#0000FF")
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var lastStartProgressAngel = START_ANGEL
        for ((index, item) in mDatas.withIndex()){
            mPaint.apply {
                color = Color.parseColor(item.color)
            }
            val percentDegress = percentToDegress(item.percent)
            if (index == 0){
                canvas.drawArc(mRect, START_ANGEL,percentDegress,true, mPaint)
            }else{
                canvas.drawArc(mRect, lastStartProgressAngel,percentDegress,true, mPaint)
            }
            lastStartProgressAngel += percentDegress + GAP_DEGRESS
        }
    }

    private fun percentToDegress(percent: Float):Float{
        return (percent / 100) * (360 - GAP_DEGRESS * mDatas.size)
    }

    private class PieBean(val name:String, val percent:Float, val color:String)
}