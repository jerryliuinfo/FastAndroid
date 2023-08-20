package com.tesla.framework.component.watcher

/**
 * Created by Jerry on 2023/8/19.
 */
interface IntHolder {

    fun addWatcher(watcher: IntWatcher):Boolean

    fun removeWatcher(watcher: IntWatcher):Boolean

    fun getValue():Int


    fun getAndWatcher(watcher: IntWatcher):Boolean{
        if (addWatcher(watcher)){
            val value = getValue()
            watcher.onValueChange(value,value)
            return true
        }
        return false
    }
}