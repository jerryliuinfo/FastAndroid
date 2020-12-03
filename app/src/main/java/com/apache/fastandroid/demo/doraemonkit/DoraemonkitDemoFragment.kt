package com.apache.fastandroid.demo.doraemonkit

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.R
import com.apache.fastandroid.bean.ViewItemBean
import com.apache.fastandroid.demo.BaseListFragment
import com.apache.fastandroid.demo.round.ConstraintBasicFragment
import com.didichuxing.doraemonkit.kit.blockmonitor.core.BlockMonitorManager
import com.tesla.framework.common.util.log.NLog
import com.tesla.framework.ui.fragment.ABaseFragment
import kotlinx.android.synthetic.main.fragment_doraemonkit_block_monitor.*

/**
 * Created by Jerry on 2020/11/11.
 */
class DoraemonkitDemoFragment:BaseListFragment() {
    companion object{
        const val TAG = "DoraemonkitDemoFragment"
    }

    override fun initDatas(): ArrayList<ViewItemBean> {
        return arrayListOf(
                ViewItemBean("BlockMonitor", "卡顿监控", BlockMonitorFragment::class.java)

        )
    }

}