package com.tesla.framework.component.locale

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import java.util.concurrent.CopyOnWriteArraySet
import com.tesla.framework.component.log.Timber

class SystemLocaleManager(context: Context) {
    private val packageManager = context.packageManager
    private val componentName = ComponentName(context, LocaleBroadcastReceiver::class.java)

    private val listeners = CopyOnWriteArraySet<SystemLocaleChangeListener>()

    @Synchronized
    fun addListener(listener: SystemLocaleChangeListener) {
        if (listeners.isEmpty()) {
            enableReceiver()
        }

        listeners.add(listener)
    }

    @Synchronized
    fun removeListener(listener: SystemLocaleChangeListener) {
        listeners.remove(listener)

        if (listeners.isEmpty()) {
            disableReceiver()
        }
    }

    internal fun notifyListeners() {
        for (listener in listeners) {
            listener.onSystemLocaleChanged()
        }
    }

    private fun enableReceiver() {
        Timber.v("Enable LocaleBroadcastReceiver")
        try {
            packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: Exception) {
            Timber.e(e, "Error enabling LocaleBroadcastReceiver")
        }
    }

    private fun disableReceiver() {
        Timber.v("Disable LocaleBroadcastReceiver")
        try {
            packageManager.setComponentEnabledSetting(
                componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        } catch (e: Exception) {
            Timber.e(e, "Error disabling LocaleBroadcastReceiver")
        }
    }
}

fun interface SystemLocaleChangeListener {
    fun onSystemLocaleChanged()
}
