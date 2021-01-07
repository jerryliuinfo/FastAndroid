package com.apache.fastandroid.demo.doraemonkit

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.didichuxing.doraemonkit.kit.blockmonitor.core.BlockMonitorManager
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_doraemonkit_block_monitor.*

/**
 * Created by Jerry on 2020/12/3.
 */
class BlockMonitorFragment:ABaseFragment() {
    override fun inflateContentView(): Int {
        return R.layout.fragment_doraemonkit_block_monitor
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceSate: Bundle?) {
        super.layoutInit(inflater, savedInstanceSate)

        BlockMonitorManager.getInstance().start()
        btn_block.setOnClickListener {
            mockBlock()
        }
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