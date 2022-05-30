package com.tesla.framework.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Jerry on 2022/5/27.
 */
class RecycleViewAtViewPage2(context: Context, attrs: AttributeSet?) : RecyclerView(context, attrs) {

    private var mStartX = 0
    private var mStartY = 0

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                mStartX = ev.x.toInt()
                mStartY = ev.y.toInt()

                //通知父 控件不要拦截 Down 事件，否则子控件会收不到事件了
                parent.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val deltaX = endX - mStartX
                val deltaY = endY - mStartY
                //水平滑动距离 大于 横向滑动距离
                if (deltaX > deltaY){
                    parent.requestDisallowInterceptTouchEvent(canScrollHorizontally(mStartX - endX))
                }else{
                    //如果是纵向滑动，告知父布局不进行时间拦截，交由子布局消费，　requestDisallowInterceptTouchEvent(true)
                    parent.requestDisallowInterceptTouchEvent(canScrollVertically(mStartX - endX))
                }
            }
            MotionEvent.ACTION_UP,MotionEvent.ACTION_CANCEL -> {
                parent.requestDisallowInterceptTouchEvent(false)
            }
        }

        return super.dispatchTouchEvent(ev)

    }
}