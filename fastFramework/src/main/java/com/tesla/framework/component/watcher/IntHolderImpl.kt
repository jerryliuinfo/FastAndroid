package com.tesla.framework.component.watcher

import com.tesla.framework.component.bean.Registry

/**
 * Created by Jerry on 2023/8/19.
 */
open class IntHolderImpl(var initValue:Int): Registry<IntWatcher>(), IntHolder {

    constructor():this(0)

    override fun addWatcher(watcher: IntWatcher): Boolean {
        return register(watcher)
    }

    override fun removeWatcher(watcher: IntWatcher): Boolean {
        return unregister(watcher)
    }


    override fun getValue(): Int {
        return initValue
    }

    fun setValue(value: Int):Int{
        val oldValue = getValue()
        if (oldValue != value){
            this.initValue = value
            onDispatchValueChanged(value,oldValue)
        }
        return oldValue
    }


    protected fun onDispatchValueChanged(to:Int, from:Int){

    }


}