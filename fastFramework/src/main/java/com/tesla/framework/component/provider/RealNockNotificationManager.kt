package com.tesla.framework.component.provider

import android.app.NotificationManager

/**
 * Created by Jerry on 2022/10/18.
 */
class RealNockNotificationManager(
    private val stockManager: NotificationManager,
    private val stringProvider: StringProvider,
    private val intentProvider: IntentProvider,
)  {
}