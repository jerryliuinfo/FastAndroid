package com.apache.fastandroid.jetpack.hit

import com.apache.fastandroid.jetpack.hit.bind.BindElectricEngine
import com.apache.fastandroid.jetpack.hit.bind.BindGasEngine
import com.apache.fastandroid.jetpack.hit.Driver
import com.apache.fastandroid.jetpack.hit.engine.Engine
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class CarInjectMultiInterface @Inject constructor(private val driver: Driver) {

    @BindGasEngine
    @Inject
    lateinit var gasEngineer: Engine

    @BindElectricEngine
    @Inject
    lateinit var electricEngineer: Engine

    fun delivery(){
        println("truck is delivery cargo, drive by${driver}")
    }

    fun deliveryByEngine(){
        gasEngineer.start()
        println("truck is delivery cargo, drive by${driver}")
        gasEngineer.shutDown()
    }

    fun deliveryByInterfaceWithTwoParams(){
        gasEngineer.start()
        electricEngineer.start()
        println("truck is delivery cargo, drive by${driver}")
        gasEngineer.shutDown()
        electricEngineer.shutDown()
    }
}