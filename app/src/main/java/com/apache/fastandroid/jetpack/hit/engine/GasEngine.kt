package com.apache.fastandroid.jetpack.hit.engine

import com.blankj.utilcode.util.ToastUtils
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class GasEngine @Inject constructor(): Engine {
    override fun start() {
        println("GasEngine start")
        ToastUtils.showShort("GasEngine start")

    }

    override fun shutDown() {
        println("GasEngine shutDown")
    }
}