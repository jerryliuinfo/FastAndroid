package com.apache.fastandroid.jetpack.hit.interfaces

import com.apache.fastandroid.jetpack.hit.bind.BindElectricEngine
import com.apache.fastandroid.jetpack.hit.bind.BindGasEngine
import com.apache.fastandroid.jetpack.hit.Driver
import com.apache.fastandroid.jetpack.hit.engine.Engine
import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class /**/CarInjectMultiInterface @Inject constructor(private val driver: Driver) {

    //注入 燃气类型的 引擎
    @BindGasEngine
    @Inject
    lateinit var gasEngineer: Engine


    //注入 电类型的 引擎
    @BindElectricEngine
    @Inject
    lateinit var electricEngineer: Engine


    fun deliveryByInterfaceWithTwoParams(){
        gasEngineer.start()
        electricEngineer.start()
        println("truck is delivery cargo, drive by${driver}")
        gasEngineer.shutDown()
        electricEngineer.shutDown()
    }
}