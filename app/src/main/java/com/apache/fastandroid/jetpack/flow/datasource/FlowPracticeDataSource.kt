package com.apache.fastandroid.jetpack.flow.datasource

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Jerry on 2022/6/19.
 */
class FlowPracticeDataSource(private val refreshInterval:Long = 3000L) {
    private var count = 0

    val countFlow:Flow<Int> = flow {
        while (true){
            emit(count++)
            delay(refreshInterval)
        }
    }
}