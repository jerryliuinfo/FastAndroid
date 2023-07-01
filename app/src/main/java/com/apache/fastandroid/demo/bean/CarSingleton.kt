package com.apache.fastandroid.demo.bean

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Jerry on 2022/3/20.
 */
@Singleton
class CarSingleton @Inject constructor() {

    fun test(){
        println("$this run test")
    }

}