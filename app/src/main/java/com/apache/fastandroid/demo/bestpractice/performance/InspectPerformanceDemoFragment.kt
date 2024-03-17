package com.apache.fastandroid.demo.bestpractice.performance

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentPerformanceJankBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2024/3/9.
 * https://developer.android.com/topic/performance/inspecting-overview?hl=zh-cn
 */
class InspectPerformanceDemoFragment:BaseBindingFragment<FragmentPerformanceJankBinding>(FragmentPerformanceJankBinding::inflate) {

    companion object{
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance:InspectPerformanceDemoFragment ?= null


    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnJank.setOnClickListener {
            mockJankUsage()
        }

        mBinding.btnMemoryShake.setOnClickListener {
            memoryShakeUsage()
        }

        mBinding.btnMemoryLeak.setOnClickListener {
            memoryLeakUsage()
        }
    }

    private fun memoryLeakUsage() {
        instance = this
    }

    private fun memoryShakeUsage() {
        Array(10000){
            it
        }.map {
            "item:$it"
        }.onEach {
            println(it)
        }
    }

    private fun mockJankUsage()
    {
        beforeClick()
        Thread.sleep(3000)
        afterClick()
    }

    private fun beforeClick(){
        println("mockJankUsage before ")
    }

    private fun afterClick(){
        println("mockJankUsage after ")
    }
}