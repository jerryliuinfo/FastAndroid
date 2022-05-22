package com.tesla.framework.component.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created by Jerry on 2022/5/21.
 */
class DialogLifeCycleObserver(private val dismiss: () ->Unit): DefaultLifecycleObserver {

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        dismiss()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        dismiss()
    }
}