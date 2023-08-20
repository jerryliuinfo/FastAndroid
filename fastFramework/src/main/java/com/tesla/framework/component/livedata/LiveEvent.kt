package com.seiko.common.util.livedata

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.tesla.framework.component.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

class LiveEvent<T> : MutableLiveData<T>() {
    private val pending = AtomicBoolean(false)
    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) {
            Timber.w("Multiple observers registered but only one will be notified of changes.")
        }
        // Observe the internal MutableLiveData
        super.observe(owner) { t ->
            if (pending.compareAndSet(true, false)) observer.onChanged(t)
        }
    }

    override fun observeForever(observer: Observer<in T>) {
        super.observeForever { if (pending.compareAndSet(true, false)) observer.onChanged(it) }
    }

    @MainThread
    override fun setValue(@Nullable t: T?) {
        pending.set(true)
        super.setValue(t)
    }

    @MainThread
    fun clear() = super.setValue(null)
}
