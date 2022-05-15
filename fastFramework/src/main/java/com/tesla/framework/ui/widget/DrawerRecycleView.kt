package com.tesla.framework.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.absoluteValue
import kotlin.math.pow

/**
 * Created by Jerry on 2022/5/10.
 */
class DrawerRecycleView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    RecyclerView(context, attrs, defStyleAttr) {

    private val touchScope = ViewConfiguration.get(context).scaledTouchSlop

    private var lastX = 0f
    private var lastY = 0f

    var isEditMode = false


    override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
        if (isEditMode){
            return super.onInterceptTouchEvent(e)
        }
        var intercpted = false

        when(e.action){
            MotionEvent.ACTION_DOWN -> {
                intercpted = false
                lastX = e.x
                lastY = e.y
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = (e.x - lastX).absoluteValue
                val deltaY = (e.y - lastY).absoluteValue
                intercpted = ( deltaX.pow(2) + deltaY.pow(2) > touchScope.toFloat().pow(2)) && deltaY > deltaX * 4
            }
            MotionEvent.ACTION_UP -> {
                intercpted = false
            }
        }

        return intercpted
    }
}