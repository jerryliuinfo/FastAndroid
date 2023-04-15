package com.tesla.framework.component.lifecycle

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.common.util.Preconditions
import com.tesla.framework.component.lifecycle.LifecycleHandler
import com.tesla.framework.component.logger.Logger

class LifecycleHandler(val lifecycleOwner: LifecycleOwner,looper: Looper = Looper.getMainLooper()) : Handler(looper), DefaultLifecycleObserver {

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {

        lifecycleOwner.lifecycle.removeObserver(this)
        removeCallbacksAndMessages(null)
    }

}