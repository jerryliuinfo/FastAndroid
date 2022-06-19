package com.apache.fastandroid.jetpack.flow.datasource

import kotlinx.coroutines.flow.map

/**
 * Created by Jerry on 2022/6/19.
 */
class FlowPracticeRepository(private val dataSource: FlowPracticeDataSource) {

    val count = dataSource.countFlow.map {value ->
        "value is ${value}"
    }
}