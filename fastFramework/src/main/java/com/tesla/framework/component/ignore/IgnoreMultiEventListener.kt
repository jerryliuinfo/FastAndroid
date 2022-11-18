package com.tesla.framework.component.ignore

import android.os.Handler
import android.os.Looper
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/11/4.
 */
class IgnoreMultiEventListener(private val maxCount:Int = 3, private val timeOut:Long = 5000,private val block: () -> Unit) : IHandleAction {
    private var count = 0
    private val mHandler = Handler(Looper.getMainLooper())
    private val mResetRunnable = Runnable {
        Logger.d("IgnoreMultiEventListener reset count to 0 ")
        count = 0
    }

    override fun onTrigger() {
        Logger.d("IgnoreMultiEventListener onTrigger count:$count")
        if (count > maxCount) {
            block()
        } else {
            ++count
            mHandler.removeCallbacks(mResetRunnable)
            mHandler.postDelayed(mResetRunnable,timeOut)
        }
    }
}