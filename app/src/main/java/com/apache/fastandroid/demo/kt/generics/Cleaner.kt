package com.apache.fastandroid.demo.kt.generics

import com.apache.fastandroid.demo.kt.generics.supply.WaterSupply

/**
 * Created by Jerry on 2022/2/27.
 */
interface Cleaner<in T: WaterSupply> {

    fun clean(waterSupply: T)

}