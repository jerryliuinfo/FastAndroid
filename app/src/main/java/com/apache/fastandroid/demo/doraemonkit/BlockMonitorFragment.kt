package com.apache.fastandroid.demo.doraemonkit

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.databinding.FragmentDoraemonkitBlockMonitorBinding
import com.didichuxing.doraemonkit.kit.blockmonitor.core.BlockMonitorManager
import com.tesla.framework.ui.fragment.ABaseFragment
import com.tesla.framework.ui.fragment.BaseTraceFragment
import kotlinx.android.synthetic.main.fragment_doraemonkit_block_monitor.*

/**
 * Created by Jerry on 2020/12/3.
 */
class BlockMonitorFragment:BaseTraceFragment<FragmentDoraemonkitBlockMonitorBinding>() {
    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        BlockMonitorManager.getInstance().start()
        btn_block.setOnClickListener {
            mockBlock()
        }
    }

    override fun inflateContentView(): Int {
        return R.layout.fragment_doraemonkit_block_monitor
    }


    private fun mockBlock() {
        try {
            view!!.postDelayed({
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }, 1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}