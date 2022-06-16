package com.apache.fastandroid.jetpack.hit

import com.apache.fastandroid.jetpack.hit.engine.ElectricEngine
import com.apache.fastandroid.jetpack.hit.engine.Engine

/**
 * Created by Jerry on 2022/6/16.
 */
class Car2(private val engine: Engine) {

    fun start(){
        engine.start()
    }
}