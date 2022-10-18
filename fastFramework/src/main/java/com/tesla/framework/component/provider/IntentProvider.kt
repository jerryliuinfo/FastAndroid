package com.tesla.framework.component.provider

import android.content.IntentFilter

/**
 * Created by Jerry on 2022/10/16.
 */
interface IntentProvider {
    fun createFilter(vararg actions: String): IntentFilter
}