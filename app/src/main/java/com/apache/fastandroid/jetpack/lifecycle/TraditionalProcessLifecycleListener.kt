package com.apache.fastandroid.jetpack.lifecycle

import android.app.Activity
import com.tesla.framework.common.fix.IMMLeaks.LifecycleCallbacksAdapter

open class TraditionalProcessLifecycleListener(private val listener: LifecycleCallbackListener?) :
    LifecycleCallbacksAdapter() {
    private var startedActivityCounter = 0

    interface LifecycleCallbackListener {
        fun onAppForeground()
        fun onAppBackground()
    }

    override fun onActivityStarted(activity: Activity) {
        synchronized(this) {
            startedActivityCounter++
            if (startedActivityCounter == 1 && listener != null) {
                listener.onAppForeground()
            }
        }
    }

    override fun onActivityStopped(activity: Activity) {
        synchronized(this) {
            startedActivityCounter--
            if (startedActivityCounter == 0 && listener != null) {
                listener.onAppBackground()
            }
        }
    }
}