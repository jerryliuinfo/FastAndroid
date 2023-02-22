package com.tesla.framework.component.locale

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.blankj.utilcode.util.Utils

class LocaleBroadcastReceiver : BroadcastReceiver() {
    private val systemLocaleManager: SystemLocaleManager = SystemLocaleManager(Utils.getApp())

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_LOCALE_CHANGED) {
            systemLocaleManager.notifyListeners()
        }
    }
}
