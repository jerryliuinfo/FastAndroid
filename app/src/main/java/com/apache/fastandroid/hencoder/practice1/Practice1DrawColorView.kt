package com.apache.fastandroid.hencoder.practice1

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.apache.fastandroid.R
import com.tesla.framework.Global

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