package com.apache.fastandroid.jetpack.lifecycle

/**
 * Created by Jerry on 2023/5/2.
 */
interface IAppStateListener {

    fun onAppForeground()

    fun onAppBackground()
}