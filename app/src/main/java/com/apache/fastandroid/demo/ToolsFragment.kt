package com.apache.fastandroid.demo

import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentToolsBinding
import com.apache.fastandroid.demo.weaknetwork.ConnectiveManagerTest
import com.apache.fastandroid.demo.weaknetwork.OkHttpNetworkTest
import com.apache.fastandroid.demo.weaknetwork.PingTest
import com.apache.fastandroid.demo.weaknetwork.TracerouteTest
import com.squareup.picasso.OkHttpDownloader
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.*

/**
 * Created by Jerry on 2022/5/22.
 */
class ToolsFragment:BaseBindingFragment<FragmentToolsBinding>(FragmentToolsBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnAdb.setOnClickListener {
            adbUsage()
        }
        mBinding.btnPlugins.setOnClickListener {
            pluginsUsage()
        }

        mBinding.btnWeakNetworkTest.setOnClickListener {
            weakNetworkTest()
        }
    }

    private fun weakNetworkTest() {
        var pingResult = PingTest().testNetwork()
//        var traceResult = TracerouteTest().testNetwork()
        var connectiveResult = ConnectiveManagerTest().testNetwork()
        Logger.d("pingResult:$pingResult, connectiveResult:$connectiveResult")


        MainScope().launch {
            withContext(Dispatchers.IO){
                val okhttpResult =  OkHttpNetworkTest().testNetwork()
                Logger.d("okhttpResult:$okhttpResult")
            }
        }
    }

    private fun pluginsUsage() {
        val url = "https://github.com/getActivity/StudioPlugins"
    }

    private fun adbUsage() {
        val url = "https://github.com/mzlogin/awesome-adb"
    }
}