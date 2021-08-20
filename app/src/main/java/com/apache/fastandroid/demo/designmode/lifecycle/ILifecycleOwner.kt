package com.apache.fastandroid.demo.designmode.lifecycle

/**
 * Created by Jerry on 2021/8/19.
 */
interface ILifecycleOwner {
    fun getLifeCycle(): ILifecycle
}