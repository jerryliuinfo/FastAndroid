package com.apache.fastandroid.demo.performance

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.tesla.framework.ui.fragment.BaseStatusFragmentNew
import kotlinx.android.synthetic.main.performance_block_cancary.*
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2020/12/28.
 */
class BlockCancaryDemoFragment : BaseStatusFragmentNew() {
    override fun inflateContentView(): Int {
        return R.layout.performance_block_cancary
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)
        btn_start.setOnClickListener {
//            BlockCanary.install(view!!.context.applicationContext, AppBlockCanaryContext()).start()
        }
        btn_thread_sleep.setOnClickListener {
            Thread.sleep(10 * 1000)

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