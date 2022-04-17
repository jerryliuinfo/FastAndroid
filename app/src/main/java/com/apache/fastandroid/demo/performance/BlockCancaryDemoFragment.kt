package com.apache.fastandroid.demo.performance

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.PerformanceBlockCancaryBinding
import com.tesla.framework.ui.fragment.BaseVBFragment
import kotlinx.android.synthetic.main.performance_block_cancary.*

/**
 * Created by Jerry on 2020/12/28.
 */
class BlockCancaryDemoFragment : BaseVBFragment<PerformanceBlockCancaryBinding>(PerformanceBlockCancaryBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        btn_start.setOnClickListener {
//            BlockCanary.install(view!!.context.applicationContext, AppBlockCanaryContext()).start()
        }
        btn_thread_sleep.setOnClickListener {
            Thread.sleep(1000)

        }

    }

    private fun startAnr(){


    }

    private fun infiniteLoop() {
        var i = 0
        while (true) {
            i++
        }
    }

}