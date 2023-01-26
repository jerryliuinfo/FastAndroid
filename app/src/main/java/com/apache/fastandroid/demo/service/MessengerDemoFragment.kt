package com.apache.fastandroid.demo.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.LayoutInflater
import android.view.View
import com.apache.fastandroid.databinding.FragmentServiceBindingBinding
import com.blankj.utilcode.util.ServiceUtils.bindService
import com.tesla.framework.kt.showToast
import com.tesla.framework.ui.fragment.BaseBindingFragment

/**
 * Created by Jerry on 2023/1/1.
 */
class MessengerDemoFragment:BaseBindingFragment<FragmentServiceBindingBinding>(FragmentServiceBindingBinding::inflate) {

    /** Messenger for communicating with the service.  */
    private var mService: Messenger? = null

    private var bound: Boolean = false


    private val mConnection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // This is called when the connection with the service has been
            // established, giving us the object we can use to
            // interact with the service.  We are communicating with the
            // service using a Messenger, so here we get a client-side
            // representation of that from the raw IBinder object.
            mService = Messenger(service)
            bound = true
        }

        override fun onServiceDisconnected(className: ComponentName) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null
            bound = false
        }
    }

    fun sayHello(v: View) {
        if (!bound) return
        val msg: Message = Message.obtain(null, MSG_SAY_HELLO, 0, 0)
        try {
            mService?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }

    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)
        mBinding.btnCallMessenger.setOnClickListener {
            sayHello(it)
        }
    }


    override fun onStart() {
        super.onStart()
        // Bind to the service
        val context = requireContext()
        Intent(context, MessengerService::class.java).also { intent ->
            bindService(intent, mConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        // Unbind from the service
        if (bound) {
            requireContext().unbindService(mConnection)
            bound = false
        }
    }

}