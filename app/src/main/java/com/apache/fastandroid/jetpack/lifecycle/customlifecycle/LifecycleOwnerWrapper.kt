package com.apache.fastandroid.jetpack.lifecycle.customlifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry

/**
 * Created by Jerry on 2022/11/22.
 */
class LifecycleOwnerWrapper(owner: LifecycleOwner) : LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(owner)

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }


    fun markState(state: Lifecycle.State) {
        lifecycleRegistry.currentState = state

    }


    fun addObserver(ob: LifecycleObserver) {
        lifecycleRegistry.addObserver(ob)
    }



}