package com.tesla.framework.component.network.k9

import android.os.Build
import android.net.ConnectivityManager as SystemConnectivityManager

interface IConnectivityManager {
    fun start()
    fun stop()
    fun isNetworkAvailable(): Boolean
    fun addListener(listener: ConnectivityChangeListener)
    fun removeListener(listener: ConnectivityChangeListener)
}

interface ConnectivityChangeListener {
    fun onConnectivityChanged()
    fun onConnectivityLost()
}

fun getMyConnectivityManager(systemConnectivityManager: SystemConnectivityManager): IConnectivityManager {
    return when {
        Build.VERSION.SDK_INT >= 24 -> ConnectivityManagerApi24(systemConnectivityManager)
        Build.VERSION.SDK_INT >= 23 -> ConnectivityManagerApi23(systemConnectivityManager)
        else -> ConnectivityManagerApi21(systemConnectivityManager)
    }
}
