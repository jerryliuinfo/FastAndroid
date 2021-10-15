package com.tesla.framework.kt

import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup

/**
 * author: jerry
 * created on: 2020/10/13 11:15 AM
 * description:
 */


/**
 * 递归地把控件的enable 状态设置为指定值
 */
fun View?.setEnableStateRecursively(enable: Boolean) {
    if (this == null) return
    if (this is ViewGroup) {
        // 递归子控件
//        this.forEach {
//            it.setEnableStateRecursively(enable)
//        }

        for (index in 1..childCount) {

        }


    }

    // 处理当前控件
    this.isEnabled = enable
}

private val sUiThreadHandler = Handler(Looper.getMainLooper())

/**
 * 在主线程中运行代码
 */
fun runOnUiThread(action: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        action.invoke()
    } else {
        sUiThreadHandler.post(action)
    }
}

/**
 * 在子线程中运行代码
 */
fun runOnBackground(action: () -> Unit) {
}

fun removeAllMessageOnUIThreadQueue() {
    sUiThreadHandler.removeCallbacksAndMessages(null)
}

/**
 * 在主线程中延后执行
 */
fun runOnUIThreadPostDelay(delayTime: Long, action: () -> Unit) {
    sUiThreadHandler.postDelayed(action, delayTime)
}

/**
 * View是否展示在界面上
 */
fun View.isDisplayed(): Boolean {
    return getGlobalVisibleRect(Rect()) && withEffectiveVisibility(this)
}

/**
 * 是否有效展示
 */
fun withEffectiveVisibility(view: View): Boolean {
    return if (view.visibility != View.VISIBLE) {
        false
    } else {
        if (view.parent is View) {
            withEffectiveVisibility(view.parent as View)
        } else {
            true
        }
    }
}

inline fun View.removeSelf(): Boolean {
    if (this.parent != null && this.parent is ViewGroup) {
        (this.parent as ViewGroup).removeView(this)
        return true
    }
    return false
}


fun View.gone() {
    if (visibility == View.VISIBLE) {
        visibility = View.GONE
    }
}

//扩展函数，view显示
fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}