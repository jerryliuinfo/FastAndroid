package com.apache.fastandroid.demo.drawable

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.kt.dp

/**
 * Created by Jerry on 2021/12/14.
 */
class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val drawable = MessDrawable()
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawable.setBounds(20.dp.toInt(),40.dp.toInt(),width,height)
        drawable.draw(canvas)
    }
}