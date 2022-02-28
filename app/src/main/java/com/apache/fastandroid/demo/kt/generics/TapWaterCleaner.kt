package com.apache.fastandroid.demo.kt.generics

import com.apache.fastandroid.demo.kt.generics.supply.impl.TapWater

/**
 * Created by Jerry on 2022/2/27.
 */
class TapWaterCleaner:Cleaner<TapWater> {
    override fun clean(waterSupply: TapWater) {
        waterSupply.addChemicalCleaners()
    }
}