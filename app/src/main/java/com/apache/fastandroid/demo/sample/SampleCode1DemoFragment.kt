package com.apache.fastandroid.demo.sample

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.view.LayoutInflater
import com.apache.fastandroid.databinding.Sample1CheckCallbackExistBinding
import com.tesla.framework.ui.fragment.BaseBindingFragment
import kotlinx.android.synthetic.main.sample1_check_callback_exist.*

/**
 * Created by  on 2021/12/18.
 */
class SampleCode1DemoFragment:BaseBindingFragment<Sample1CheckCallbackExistBinding>(Sample1CheckCallbackExistBinding::inflate) {

    companion object {
        private const val HANDLE_MSG = 0
        private const val THRESHOLD_IN_MS = 1000L
        private const val MAGNIFIER_FRAME_CALLBACK_THREAD_NAME = "magnifier_frame_callback_thread"

    }

    private var handlerThread: HandlerThread = HandlerThread(MAGNIFIER_FRAME_CALLBACK_THREAD_NAME).also { it.start() }

    private var handler: Handler = object : Handler(handlerThread.looper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                HANDLE_MSG -> notifyListener()
            }
        }
    }

    private fun notifyListener() {
       /* listener.invoke(frameList.size)
        frameList.clear()*/
        handler.sendEmptyMessageDelayed(HANDLE_MSG, THRESHOLD_IN_MS)
    }

    fun start() {
        handler.sendEmptyMessageDelayed(HANDLE_MSG, THRESHOLD_IN_MS)
    }

    fun stop() {
        handler.removeCallbacksAndMessages(null)
        handlerThread.quit()
        handlerThread.interrupt()
    }

    override fun layoutInit(inflater: LayoutInflater?, savedInstanceState: Bundle?) {
        super.layoutInit(inflater, savedInstanceState)

        var owner = BuilderOwner.Builder()
            .addCallback(BuilderOwner.CallbackImpl1())
            .addCallback(BuilderOwner.CallbackImpl2())
            .addCallback(BuilderOwner.CallbackImpl3()).build()
        owner.register()
        btn_check_callback.setOnClickListener {
            owner.showCallback(BuilderOwner.CallbackImpl1::class.java)
        }

        btn_handler_poll.setOnClickListener {
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stop()
    }


}