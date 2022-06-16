package com.apache.fastandroid.jetpack.hit

import com.apache.fastandroid.jetpack.hit.engine.ElectricEngine
import com.apache.fastandroid.jetpack.hit.engine.Engine
import com.apache.fastandroid.jetpack.hit.tyre.Tyre
import javax.inject.Inject

/**
 * Created by Jerry on 2022/6/16.
 */
class CarParam @Inject constructor(private val driver: Driver) {

    fun start(){
        driver.drive()
    }
}