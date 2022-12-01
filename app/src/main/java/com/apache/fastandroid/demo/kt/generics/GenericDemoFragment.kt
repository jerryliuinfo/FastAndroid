package com.apache.fastandroid.demo.kt.generics

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentGenericTypeBinding
import com.apache.fastandroid.demo.kt.generics.supply.WaterSupply
import com.apache.fastandroid.demo.kt.generics.supply.impl.LakeWater
import com.apache.fastandroid.demo.kt.generics.supply.impl.TapWater
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2022/2/27.
 */
class GenericDemoFragment:BaseBindingFragment<FragmentGenericTypeBinding>(FragmentGenericTypeBinding::inflate) {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnGeneric.setOnClickListener {
            genericUsage()
        }

        mBinding.btnInout.setOnClickListener {
            inOutUsage()
        }

        mBinding.btnGeneric2.setOnClickListener {
            genericUsage2()
        }
        mBinding.btnRefied.setOnClickListener {
            refiedUsage()
        }
    }

    private fun refiedUsage() {
        val aquarium = Aquarium(TapWater())
        var tapWaterType = aquarium.hasWaterSupplyOfType<TapWater>()
        var lakeWaterType = aquarium.hasWaterSupplyOfType<LakeWater>()
        println("$tapWaterType $lakeWaterType")
    }

    private fun genericUsage2() {
        val cleaner = TapWaterCleaner()
        val aquarium = Aquarium(TapWater())
        aquarium.addWater(cleaner)
    }

    private fun inOutUsage() {

        val aquarium = Aquarium(TapWater())
        addItemto(aquarium)

        isWaterClean(aquarium)
    }

    private fun genericUsage() {
        val aquarium = Aquarium(TapWater())
        println("water needs processing before: ${aquarium.waterSupply.needProcessing}")
        aquarium.waterSupply.addChemicalCleaners()
        println("water needs processing after: ${aquarium.waterSupply.needProcessing}")

        val aquarium2 = Aquarium(LakeWater())
        aquarium2.waterSupply.filter()
        aquarium2.addWater()

    }
    fun addItemto(aquarium: Aquarium<WaterSupply>) = println("item added ")


    fun <T:WaterSupply> isWaterClean(aquarium: Aquarium<T>){
        println("aquarium water is clean: ${!aquarium.waterSupply.needProcessing}")

    }
}
