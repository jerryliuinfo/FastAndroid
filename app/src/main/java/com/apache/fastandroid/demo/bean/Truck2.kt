package com.apache.fastandroid.demo.bean

import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class Truck2 @Inject constructor(private val driver: Driver) {

    @BindGasEngine
    @Inject
    lateinit var gasEngineer: Engineer

    @BindElectricEngine
    @Inject
    lateinit var electricEngineer: Engineer

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