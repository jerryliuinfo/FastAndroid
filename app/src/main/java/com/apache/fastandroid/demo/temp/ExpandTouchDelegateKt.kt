package com.apache.fastandroid.demo.temp

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup

/**
 * Created by Jerry on 2021/5/28.
 */
class ExpandTouchDelegateKt(val deleateView:View) {
    private var parent:ViewGroup = deleateView.parent as ViewGroup

    /**
     * 扩大点击区域，需要传入负值
     *
     */
    fun  expandArea(dx:Int, dy:Int){
        deleateView.post {
            val delegateRect:Rect = Rect()
            deleateView.getHitRect(delegateRect)
            delegateRect.inset(dx,dy)
            parent.touchDelegate = TouchDelegate(delegateRect,deleateView)
        }
    }
}