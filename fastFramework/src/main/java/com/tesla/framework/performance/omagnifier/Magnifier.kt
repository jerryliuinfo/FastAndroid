/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.tesla.framework.performance.omagnifier

import androidx.annotation.UiThread
import com.tesla.framework.performance.omagnifier.frame.FPSMonitor
import com.tesla.framework.performance.omagnifier.frame.FPSMonitorConfig
import com.tesla.framework.performance.omagnifier.memory.MemoryMonitor
import com.tesla.framework.performance.omagnifier.memory.MemoryMonitorConfig

object Magnifier {

    private val fpsMonitor: FPSMonitor by lazy { FPSMonitor() }

    private val memoryMonitor: MemoryMonitor by lazy { MemoryMonitor() }

    @UiThread
    fun startMonitorFPS(config: FPSMonitorConfig) {
        fpsMonitor.startMonitorFPS(config)
    }

    @UiThread
    fun stopMonitorFPS() {
        fpsMonitor.stopMonitorFPS()
    }

    fun isEnabledFPSMonitor(): Boolean {
        return fpsMonitor.isFPSMonitorEnabled()
    }

    fun startMonitorMemory(config: MemoryMonitorConfig) {
        memoryMonitor.start(config)
    }

    fun stopMonitorMemory() {
        memoryMonitor.stop()
    }

    fun dumpMemoryImmediately(onSampleListener: MemoryMonitor.OnSampleListener) {
        memoryMonitor.dumpImmediately(onSampleListener)
    }

    fun isEnabledMemoryMonitor(): Boolean {
        return memoryMonitor.isMonitorEnabled()
    }
}