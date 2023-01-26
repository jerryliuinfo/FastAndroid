package com.apache.fastandroid.demo.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.FragmentServiceBindingBinding
import com.blankj.utilcode.util.ServiceUtils.bindService
import com.tesla.framework.kt.showToast
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/1/1.
 */
class ServiceDemoFragment:BaseBindingFragment<FragmentServiceBindingBinding>(FragmentServiceBindingBinding::inflate) {

    private lateinit var mService: LocalService

    private var mBound:Boolean = false


    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as LocalService.LocalBinder
            mService = binder.getService()
            mBound = true
        }

        /**
         * 当与服务的连接意外中断时，例如服务崩溃或被终止时，Android 系统会调用该方法。当客户端取消绑定时，系统不会调用该方法。
         */
        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    private val aidlServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {

        }

        /**
         * 当与服务的连接意外中断时，例如服务崩溃或被终止时，Android 系统会调用该方法。当客户端取消绑定时，系统不会调用该方法。
         */
        override fun onServiceDisconnected(arg0: ComponentName) {

        }
    }

    override fun onStart() {
        super.onStart()
        val context = requireContext()
        Intent(context, LocalService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        Intent(context, AidlService::class.java).also { intent ->
            bindService(intent, aidlServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
//        if (mBound){
//            requireContext().unbindService(connection)
//            mBound = false
//        }
    }


    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        mBinding.btnCallService.setOnClickListener {
            if (mBound){
                val num = mService.randomNumber
                showToast("randNum: $num")
            }
        }

        mBinding.btnCallAidlService.setOnClickListener {

        }
    }
}