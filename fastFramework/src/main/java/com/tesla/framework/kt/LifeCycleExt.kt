package com.tesla.framework.kt

import android.app.Dialog
import androidx.lifecycle.LifecycleOwner
import com.tesla.framework.component.lifecycle.DialogLifeCycleObserver
import java.lang.IllegalStateException

/**
 * Created by Jerry on 2022/5/21.
 */

fun Dialog.lifeCycleOwner(owner: LifecycleOwner? = null): Dialog {
    val observer = DialogLifeCycleObserver(::dismiss)
    val lifecycleOwner = owner ?: (context as? LifecycleOwner
        ?: throw IllegalStateException("context is not a LifecycleOwner"))
    lifecycleOwner.lifecycle.addObserver(observer)
    return this
}