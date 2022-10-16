package com.apache.fastandroid.jetpack.hit

import com.apache.fastandroid.jetpack.hit.engine.ElectricEngine
import com.apache.fastandroid.jetpack.hit.engine.Engine

/**
 * Created by Jerry on 2022/6/16.
 */
class Car {
    private val engine:Engine = ElectricEngine()

    fun start(){
        engine.start()
    }

    var gallonsOfFuleInTank: Int = 15

        //外界无法调用set，只能调用get
        private set
}