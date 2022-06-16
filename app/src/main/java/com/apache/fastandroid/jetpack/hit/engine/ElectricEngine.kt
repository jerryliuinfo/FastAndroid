package com.apache.fastandroid.jetpack.hit.engine

import com.blankj.utilcode.util.ToastUtils
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class ElectricEngine @Inject constructor(): Engine {
    override fun start() {
        println("ElectricEngine start")
        ToastUtils.showShort("ElectricEngine start")
    }

    override fun shutDown() {
        println("ElectricEngine shutDown")
    }
}