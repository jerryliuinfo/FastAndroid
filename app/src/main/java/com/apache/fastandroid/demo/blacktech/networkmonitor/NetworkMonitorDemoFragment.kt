package com.apache.fastandroid.demo.blacktech.networkmonitor

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentNetworkMonitorDemoBinding
import com.linkaipeng.oknetworkmonitor.ui.RequestsActivity
import com.linkaipeng.oknetworkmonitor.ui.RequestsActivity.Companion.starter
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/3/11.
 */
class NetworkMonitorDemoFragment:BaseBindingFragment<FragmentNetworkMonitorDemoBinding>(FragmentNetworkMonitorDemoBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnNetworkMonitor.setOnClickListener {
            RequestsActivity.starter(requireContext())
        }
    }
}