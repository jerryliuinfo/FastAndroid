package com.apache.fastandroid.hencoder.basic

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by Jerry on 2020/11/11.
 */
class Practice1DrawColorView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        //画一个蒙版
        canvas.drawColor(Color.parseColor("#88880000"))
    }
}