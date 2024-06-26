/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.tesla.framework.performance.omagnifier.memory

class ThreadMetricCollector : IMetricCollector<ThreadInfo> {

    override fun collect(): ThreadInfo {
        val allStackTraces = Thread.getAllStackTraces()
        return ThreadInfo(readThreadsCount(), allStackTraces)
    }
}

data class ThreadInfo(
    val threadsCount: Long,
    val threadMap: Map<Thread, Array<StackTraceElement>>
)