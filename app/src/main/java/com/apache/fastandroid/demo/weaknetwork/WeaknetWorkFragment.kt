package com.apache.fastandroid.demo.weaknetwork

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentWeakNetworkBinding
import com.apache.fastandroid.demo.weaknetwork.connectionclass.ConnectionClassActivity
import com.tesla.framework.component.logger.Logger
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Jerry on 2022/5/22.
 */
class WeaknetWorkFragment:BaseBindingFragment<FragmentWeakNetworkBinding>(FragmentWeakNetworkBinding::inflate) {

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)



        mBinding.btnPing.setOnClickListener {
            weakNetworkByPing()
        }

        mBinding.btnConnectiveManager.setOnClickListener {
            weakNetworkByConnectiveManager()
        }

        mBinding.btnConnectiveManager.setOnClickListener {
            weakNetworkByConnectiveManager()
        }
        mBinding.btnDownloadFile.setOnClickListener {
            weakNetworkByDownloadFile()
        }

        mBinding.btnConnection.setOnClickListener {
            requireActivity().startActivity(Intent(requireContext(),ConnectionClassActivity::class.java))
        }

        mBinding.btnConnectiveManager2.setOnClickListener {
            weakNetworkByConnectiveManager2()
        }
    }

    private fun weakNetworkByPing() {
        MainScope().launch {
            withContext(Dispatchers.IO){
                val result = PingTest().testNetwork()
                Logger.d("test weak network by ping:$result")
            }
        }
    }

    private fun weakNetworkByDownloadFile() {
        MainScope().launch {
            withContext(Dispatchers.IO){
                val okhttpResult =  OkHttpNetworkTest().testNetwork()
                Logger.d("okhttpResult:$okhttpResult")
            }
        }
    }

    private fun weakNetworkByConnectiveManager() {
        MainScope().launch {
            withContext(Dispatchers.IO){
                var result = ConnectiveManagerTest().testNetwork()
                Logger.d("test weak network by ConnectiveManager:$result")
            }
        }


    }

    private fun weakNetworkByConnectiveManager2() {
        MainScope().launch {
            withContext(Dispatchers.IO){
                var result = ConnectiveManagerTest2().testNetwork()
                Logger.d("test weak network by ConnectiveManager:$result")
            }
        }

    }




}