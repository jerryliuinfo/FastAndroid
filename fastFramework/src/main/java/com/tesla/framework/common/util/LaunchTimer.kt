package com.tesla.framework.common.util

import com.tesla.framework.component.logger.Logger

object LaunchTimer {
    val TAG = LaunchTimer::class.java.simpleName
    private var sTime: Long = 0
    fun startRecord() {
        sTime = System.currentTimeMillis()

    }

    @JvmOverloads
    fun endRecord(msg: String = "") {
        val cost = System.currentTimeMillis() - sTime
        Logger.d("$msg cost time: $cost ms")
    }
}