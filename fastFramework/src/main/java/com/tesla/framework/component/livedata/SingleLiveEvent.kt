package com.tesla.framework.component.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.tesla.framework.component.logger.Logger
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by Jerry on 2022/1/22.
 */
class SingleLiveEvent<T> : MutableLiveData<T>() {
    private val mPending = AtomicBoolean(false)
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {

        if (hasActiveObservers()) {
            Logger.w("Multiple observers registered but only one will be notified of changes.")
        }

        super.observe(owner) { t ->
            println("SingleLiveEvent onChange: ${mPending.get()}")
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    @MainThread
    override fun setValue(t: T?) {
        mPending.set(true)
        super.setValue(t)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        //会调用  setValue(t: T?) 将 mPending 设置为 true
        value = null
    }
}