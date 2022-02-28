package com.apache.fastandroid.demo.kt.generics

import com.apache.fastandroid.demo.kt.generics.supply.WaterSupply

/**
 * Created by Jerry on 2022/2/27.
 * 鱼缸
 */
class Aquarium<out T: WaterSupply>(val waterSupply: T){

    fun addWater(){
        check(!waterSupply.needProcessing){
            println("water supply needs processing first")
        }
        println("adding water from $waterSupply")
    }

    fun addWater(cleaner: Cleaner<T>){
        if (waterSupply.needProcessing){
            cleaner.clean(waterSupply)
        }
        println("water added")
    }

    inline fun <reified R:WaterSupply> hasWaterSupplyOfType() = waterSupply is R


}