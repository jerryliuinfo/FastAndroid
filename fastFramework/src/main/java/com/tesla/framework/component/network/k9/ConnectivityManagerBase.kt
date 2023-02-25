package com.fsck.k9.network

import com.tesla.framework.component.network.k9.ConnectivityChangeListener
import com.tesla.framework.component.network.k9.IConnectivityManager
import java.util.concurrent.CopyOnWriteArraySet

internal abstract class ConnectivityManagerBase : IConnectivityManager {
    private val listeners = CopyOnWriteArraySet<ConnectivityChangeListener>()

    @Synchronized
    override fun addListener(listener: ConnectivityChangeListener) {
        listeners.add(listener)
    }

    @Synchronized
    override fun removeListener(listener: ConnectivityChangeListener) {
        listeners.remove(listener)
    }

    @Synchronized
    protected fun notifyOnConnectivityChanged() {
        for (listener in listeners) {
            listener.onConnectivityChanged()
        }
    }

    @Synchronized
    protected fun notifyOnConnectivityLost() {
        for (listener in listeners) {
            listener.onConnectivityLost()
        }
    }
}
