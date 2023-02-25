package com.apache.fastandroid.demo.bestpractice.network

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentNetworkDemoBinding
import com.blankj.utilcode.util.ToastUtils
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.component.network.AutoRegisterNetListener
import com.tesla.framework.component.network.k9.ConnectivityChangeListener
import com.tesla.framework.component.network.k9.getMyConnectivityManager
import com.tesla.framework.kt.connectivityManager
import com.tesla.framework.ui.activity.BaseVBFragment2
import com.zwb.lib_base.utils.network.NetworkStateChangeListener
import com.zwb.lib_base.utils.network.NetworkTypeEnum

/**
 * Created by Jerry on 2023/2/24.
 */
class NetworkDemoFragment: BaseVBFragment2<FragmentNetworkDemoBinding>(),
    NetworkStateChangeListener {

    private val mConnectiveManager by lazy {
        getMyConnectivityManager(requireContext().connectivityManager)
    }

    private val mListener = object :ConnectivityChangeListener{
        override fun onConnectivityChanged() {
            Logger.d("onConnectivityChanged -->")
            ToastUtils.showShort("onConnectivityChanged-->")
        }

        override fun onConnectivityLost() {
            Logger.d("onConnectivityLost -->")
            ToastUtils.showShort("onConnectivityLost-->")

        }

    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)


        mBinding.btnStartMonitor.setOnClickListener {
            mConnectiveManager.addListener(mListener)
            mConnectiveManager.start()
        }

        mBinding.btnStopMonitor.setOnClickListener {
            mConnectiveManager.removeListener(mListener)
            mConnectiveManager.stop()
        }

        lifecycle.addObserver(AutoRegisterNetListener(this))

    }

    override fun networkTypeChange(type: NetworkTypeEnum) {
        ToastUtils.showShort("networkTypeChange-->")


    }

    override fun networkConnectChange(isConnected: Boolean) {
        ToastUtils.showShort("networkConnectChange-->")
    }


}