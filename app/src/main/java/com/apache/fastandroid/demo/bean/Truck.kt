package com.apache.fastandroid.demo.bean

import javax.inject.Inject

/**
 * Created by Jerry on 2022/3/20.
 */
class Truck @Inject constructor() {

    fun delivery(){
        println("truck deilvier")
    }
}