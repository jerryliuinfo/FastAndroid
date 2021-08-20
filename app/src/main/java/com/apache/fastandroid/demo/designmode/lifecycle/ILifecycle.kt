package com.apache.fastandroid.demo.designmode.lifecycle

/**
 * Created by Jerry on 2021/8/19.
 */
abstract class ILifecycle {
   abstract fun addObserver(observer: ILifecycleObserver);
   abstract fun removeObserver(observer: ILifecycleObserver);
}