package com.apache.fastandroid.jetpack.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/6/9.
 */
class LifecyclerChecker: DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Logger.d("LifecyclerChecker onStart onAppForeground")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Logger.d("LifecyclerChecker onStop onAppBackground")
    }


}