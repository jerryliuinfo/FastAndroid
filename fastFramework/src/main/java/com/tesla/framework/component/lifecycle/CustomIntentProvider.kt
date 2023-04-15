package com.tesla.framework.component.lifecycle

import android.content.IntentFilter
import com.tesla.framework.component.provider.IntentProvider

/**
 * Created by Jerry on 2023/3/27.
 */
class CustomIntentProvider:IntentProvider {
    override fun createFilter(vararg actions: String): IntentFilter {
        return IntentFilter().apply {
            for (action in actions){
                addAction(action)
            }
        }
    }
}