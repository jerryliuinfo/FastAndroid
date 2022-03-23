package com.apache.fastandroid.demo.bean

import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class ElectricEngine @Inject constructor():Engineer {
    override fun start() {
        println("ElectricEngine start")
    }

    override fun shutDown() {
        println("ElectricEngine shutDown")
    }
}