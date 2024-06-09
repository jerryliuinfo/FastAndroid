package com.tesla.framework.kt

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.tesla.framework.component.lifecycle.DialogLifeCycleObserver

/**
 * Created by Jerry on 2022/5/21.
 */


fun Dialog.lifeCycleOwner(owner: LifecycleOwner? = null): Dialog {
    val observer = DialogLifeCycleObserver {
        dismiss()
    }
    val lifecycleOwner = owner ?: (context as? LifecycleOwner
        ?: throw IllegalStateException("context is not a LifecycleOwner"))
    lifecycleOwner.lifecycle.addObserver(observer)
    return this
}



fun Application.doOnActivityLifecycle(
    onActivityCreated: ((Activity, Bundle?) -> Unit)? = null,
    onActivityStarted: ((Activity) -> Unit)? = null,
    onActivityResumed: ((Activity) -> Unit)? = null,
    onActivityPaused: ((Activity) -> Unit)? = null,
    onActivityStopped: ((Activity) -> Unit)? = null,
    onActivitySaveInstanceState: ((Activity, Bundle?) -> Unit)? = null,
    onActivityDestroyed: ((Activity) -> Unit)? = null,
): Application.ActivityLifecycleCallbacks =
    object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            onActivityCreated?.invoke(activity, savedInstanceState)
        }

        override fun onActivityStarted(activity: Activity) {
            onActivityStarted?.invoke(activity)
        }

        override fun onActivityResumed(activity: Activity) {
            onActivityResumed?.invoke(activity)
        }

        override fun onActivityPaused(activity: Activity) {
            onActivityPaused?.invoke(activity)
        }

        override fun onActivityStopped(activity: Activity) {
            onActivityStopped?.invoke(activity)
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            onActivitySaveInstanceState?.invoke(activity, outState)
        }

        override fun onActivityDestroyed(activity: Activity) {
            onActivityDestroyed?.invoke(activity)
        }
    }.also {
        registerActivityLifecycleCallbacks(it)
    }

fun Fragment.doOnViewLifecycle(
    onCreateView: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onPause: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    onDestroyView: (() -> Unit)? = null,
) =
    viewLifecycleOwner.doOnLifecycle(onCreateView, onStart, onResume, onPause, onStop, onDestroyView)

fun LifecycleOwner.doOnLifecycle(
    onCreate: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onPause: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    onDestroy: (() -> Unit)? = null,
) =
    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            onCreate?.invoke()
        }

        override fun onStart(owner: LifecycleOwner) {
            onStart?.invoke()
        }

        override fun onResume(owner: LifecycleOwner) {
            onResume?.invoke()
        }

        override fun onPause(owner: LifecycleOwner) {
            onPause?.invoke()
        }

        override fun onStop(owner: LifecycleOwner) {
            onStop?.invoke()
        }

        override fun onDestroy(owner: LifecycleOwner) {
            onDestroy?.invoke()
        }
    })

val Fragment.viewLifecycleScope get() = viewLifecycleOwner.lifecycleScope

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: Observer<T>) {
    liveData.observe(this, observer)
}

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>