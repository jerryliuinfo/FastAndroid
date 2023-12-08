package com.apache.fastandroid.demo.performance.perfetto

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentPerfettoBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/5/9.
 */
class PerfettoDemoFragment :
    BaseBindingFragment<FragmentPerfettoBinding>(FragmentPerfettoBinding::inflate) {


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        refresh()
        mBinding.btnSimulateJank.setOnClickListener {


            refresh()
        }

    }

    private fun refresh() {
        mBinding.btnSimulateJank.text = "Loading"

        val duration = kotlin.random.Random.nextInt(4000, 6000)
        Thread.sleep(duration.toLong())

        mBinding.btnSimulateJank.text = "加载结果${duration}"
    }


}