package com.tesla.framework.common.util

import android.os.Handler
import android.os.Looper

/**
 * author: jerry
 * created on: 2020/10/9 2:42 PM
 * description:
 */

private val sUiThreadHandler = Handler(Looper.getMainLooper())

/**
 * 在主线程中运行代码
 */
fun runOnUiThread(action: () -> Unit ) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        action.invoke()
    } else {
        sUiThreadHandler.post(action)
    }
}

fun postOnUiThread(action: () -> Unit ) {
    sUiThreadHandler.post(action)
}

fun postDelay(runnable: Runnable, delay: Long){
    sUiThreadHandler.postDelayed(runnable, delay)
}

fun cancel(runnable: Runnable) {
    sUiThreadHandler.removeCallbacks(runnable)
}

private fun postDelay(action: () -> Unit, delay: Long) {
    sUiThreadHandler.postDelayed(action, delay)
}