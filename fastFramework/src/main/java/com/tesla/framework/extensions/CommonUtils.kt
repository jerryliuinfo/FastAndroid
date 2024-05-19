package com.tesla.framework.extensions

import android.os.Looper

/**
 * Created by Jerry on 2024/5/19.
 */
internal fun checkMainThread() {
    check(Looper.getMainLooper() === Looper.myLooper()) {
        "The method must be called on the main thread"
    }
}

internal fun checkMainThread(reason: String) {
    check(Looper.getMainLooper() === Looper.myLooper()) {
        "The method must be called on the main thread. Reason: $reason."
    }
}