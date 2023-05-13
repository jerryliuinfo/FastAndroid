package com.tesla.framework.kt

import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2023/5/2.
 */
class ConfirmCallback(private var action: ((isConfirm:Boolean) -> Unit)?) {

    operator fun invoke(isConfirm:Boolean){

        if (action == null){
            Logger.w("Confirm callback invoked more than once, ignored after first invocation.")
        }
        action?.invoke(isConfirm)
        action = null
    }
}