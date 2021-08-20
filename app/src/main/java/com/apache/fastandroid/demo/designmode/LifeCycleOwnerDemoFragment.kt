package com.apache.fastandroid.demo.designmode

import com.apache.fastandroid.demo.designmode.lifecycle.ILifecycle
import com.apache.fastandroid.demo.designmode.lifecycle.ILifecycleOwner
import com.apache.fastandroid.demo.designmode.lifecycle.LifecycleRegister

/**
 * Created by Jerry on 2021/8/19.
 */
class LifeCycleOwnerDemoFragment: ILifecycleOwner {
    private val lifecycleRegister = LifecycleRegister(this)
    override fun getLifeCycle(): ILifecycle {
        return lifecycleRegister
    }
}