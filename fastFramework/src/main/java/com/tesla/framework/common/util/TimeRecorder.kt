package com.tesla.framework.common.util

/**
 * Created by Jerry on 2023/11/21.
 */
class TimeRecorder {

    private var startTime: Long = 0L
    private var endTime: Long = 0L

    fun start() {
        startTime = System.currentTimeMillis()
    }

    fun end() {
        endTime = System.currentTimeMillis()
    }

    override fun toString(): String {
        return "Time consumed: ${endTime - startTime}ms."
    }
}