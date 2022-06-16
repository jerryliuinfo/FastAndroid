package com.apache.fastandroid.jetpack.hit

import com.apache.fastandroid.jetpack.hit.engine.ElectricEngine
import com.apache.fastandroid.jetpack.hit.engine.Engine
import com.apache.fastandroid.jetpack.hit.tyre.Tyre

/**
 * Created by Jerry on 2022/6/16.
 */
class Car3(private val engine: Engine,private val tyre: Tyre) {

    fun start(){
        engine.start()
    }
}