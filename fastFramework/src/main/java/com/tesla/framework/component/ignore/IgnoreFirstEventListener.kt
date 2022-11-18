package com.tesla.framework.component.ignore

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2022/11/4.
 */
class IgnoreFirstEventListener(private val block: () -> Unit) : IHandleAction {
    private var firstEvent = true
    override fun onTrigger() {
        Logger.d("IgnoreFirstEventListener onTrigger:$firstEvent")
        firstEvent = if (!firstEvent) {
            block()
            true
        } else {
            false
        }
    }
}