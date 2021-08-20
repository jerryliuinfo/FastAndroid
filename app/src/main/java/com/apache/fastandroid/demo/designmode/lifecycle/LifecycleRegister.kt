package com.apache.fastandroid.demo.designmode.lifecycle

import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference

/**
 * Created by Jerry on 2021/8/19.
 */
class LifecycleRegister(private val lifecycleOwner: ILifecycleOwner): ILifecycle() {
    private var mLifecycleOwner: WeakReference<ILifecycleOwner>? = null
    init {
        mLifecycleOwner = WeakReference(lifecycleOwner)
    }


    override fun addObserver(observer: ILifecycleObserver) {
    }

    override fun removeObserver(observer: ILifecycleObserver) {
    }
}