/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.tesla.framework.performance.omagnifier.memory

class FileDescriptorMetricCollector : IMetricCollector<FileDescriptorInfo> {

    override fun collect(): FileDescriptorInfo {
        return FileDescriptorInfo(readMaxOpenFiles(), readFileDescriptors())
    }
}

data class FileDescriptorInfo(
    val fdMaxCount: Long,
    val fileDescriptors: List<String>
)