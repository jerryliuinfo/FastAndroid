package com.apache.fastandroid.demo.bean

import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class GasEngine @Inject constructor():Engineer {
    override fun start() {
        println("GasEngine start")
    }

    override fun shutDown() {
        println("GasEngine shutDown")
    }
}