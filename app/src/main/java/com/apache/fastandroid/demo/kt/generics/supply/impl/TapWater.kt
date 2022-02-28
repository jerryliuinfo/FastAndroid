package com.apache.fastandroid.demo.kt.generics.supply.impl

import com.apache.fastandroid.demo.kt.generics.supply.WaterSupply

/**
 * Created by Jerry on 2022/2/27.
 * 自来水
 */
class TapWater: WaterSupply(true) {

    fun addChemicalCleaners(){
        needProcessing = false
    }
}