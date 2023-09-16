package com.tesla.framework.component.keyboardvisibilityevent

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by Piasy{github.com/Piasy} on 8/18/16.
 */

abstract class AutoActivityLifecycleCallback internal constructor(
    private val targetActivity: Activity
) : com.tesla.framework.common.fix.IMMLeaks.LifecycleCallbacksAdapter() {

    override fun onActivityDestroyed(activity: Activity) {
        if (activity === targetActivity) {
            targetActivity.application.unregisterActivityLifecycleCallbacks(this)
            onTargetActivityDestroyed()
        }
    }

    protected abstract fun onTargetActivityDestroyed()
}
