package com.apache.fastandroid.jetpack.lifecycle

import android.content.Context
import android.location.Location
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

/**
 * Created by Jerry on 2022/11/20.
 */
class MyLocationListener(
    private val context: Context,
    private val lifecycle: Lifecycle,
    private val callback: (Location) -> Unit

):DefaultLifecycleObserver {

    private var enabled = false

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (enabled){
            //connect
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        //disconnect if connected
    }

    fun enable(){
        enabled = true
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)){
            // connect if not connected
        }
    }

}