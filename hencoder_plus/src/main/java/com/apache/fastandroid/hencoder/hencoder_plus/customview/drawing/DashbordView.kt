package com.apache.fastandroid.hencoder.hencoder_plus.customview.drawing

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.tesla.framework.kt.dp
import com.tesla.framework.common.util.log.NLog


private val RADIS = 150.dp
private val OPEN_ANGEL = 120
private val DASH_WIDTH = 2.dp
private val DASH_LENGTH = 10.dp



/**
 * Created by Jerry on 2021/7/28.
 */
class DashbordView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    companion object{
        private val TAG = "DashbordView "
    }
    private val rect = RectF()
    private val dash = Path()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        NLog.d(TAG, "apply")
        style = Paint.Style.STROKE
        strokeWidth = 3f.dp
        color = Color.RED
    }

    init {
        NLog.d(TAG, "init")
        dash.addRect(0f,0f, DASH_WIDTH, DASH_LENGTH,Path.Direction.CCW)
        paint.setPathEffect(PathDashPathEffect(dash,50f,0f,PathDashPathEffect.Style.ROTATE))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        NLog.d(TAG, "width:${measuredWidth}, h: ${measuredHeight}" )
        rect.set(width / 2 - RADIS,height / 2 - RADIS,width /2 + RADIS, height / 2 + RADIS)
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

       canvas.drawArc(rect, (90+ OPEN_ANGEL / 2).toFloat(), (360 - OPEN_ANGEL).toFloat(), false,paint )
    }
}