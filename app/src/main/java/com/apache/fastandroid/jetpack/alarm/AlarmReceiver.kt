package com.apache.fastandroid.jetpack.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.tesla.framework.component.logger.Logger

/**
 * Created by Jerry on 2024/1/28.
 */
class AlarmReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Logger.d("AlarmReceiver onReceive")
    }
}