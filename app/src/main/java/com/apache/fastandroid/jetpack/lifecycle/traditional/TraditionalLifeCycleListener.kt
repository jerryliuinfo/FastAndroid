package com.apache.fastandroid.jetpack.lifecycle.traditional

import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.common.util.log.FastLog

/**
 * Created by Jerry on 2020/11/1.
 */
class TraditionalLifeCycleListener {

    companion object{
        const val TAG = "TraditionalLifeCycleListener"
    }

    fun onCreate2(owner: LifecycleOwner) {
        FastLog.d(TAG, "TraditionalLifeCycleListener onCreate---->")
    }

    fun onDestroy(owner: LifecycleOwner) {
        FastLog.d(TAG, "TraditionalLifeCycleListener onDestroy---->")
    }

}