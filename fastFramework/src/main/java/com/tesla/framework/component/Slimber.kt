package com.tesla.framework.component

/**
 * Created by Jerry on 2022/5/27.
 */

import timber.log.Timber

/** Invokes an action if any trees are planted */
inline fun ifPlanted(action: () -> Unit) {
    if (Timber.treeCount != 0) {
        action()
    }
}

/** Delegates the provided message to [Timber.e] if any trees are planted. */
inline fun logE(message: () -> String) = ifPlanted { Timber.e(message()) }

/** Delegates the provided message to [Timber.e] if any trees are planted. */
inline fun logE(throwable: Throwable, message: () -> String) = ifPlanted { Timber.e(throwable, message()) }

/** Delegates the throwable to [Timber.e]. Made for convenience when called from java */
fun logE(throwable: Throwable) = Timber.e(throwable)

/** Delegates the provided message to [Timber.w] if any trees are planted. */
inline fun logW(message: () -> String) = ifPlanted { Timber.w(message()) }

/** Delegates the provided message to [Timber.w] if any trees are planted. */
inline fun logW(throwable: Throwable, message: () -> String) = ifPlanted { Timber.w(throwable, message()) }

/** Delegates the throwable to [Timber.w]. Made for convenience when called from java */
fun logW(throwable: Throwable) = Timber.w(throwable)

/** Delegates the provided message to [Timber.i] if any trees are planted. */
inline fun logI(message: () -> String) = ifPlanted { Timber.i(message()) }

/** Delegates the provided message to [Timber.i] if any trees are planted. */
inline fun logI(throwable: Throwable, message: () -> String) = ifPlanted { Timber.i(throwable, message()) }

/** Delegates the throwable to [Timber.i]. Made for convenience when called from java */
fun logI(throwable: Throwable) = Timber.i(throwable)

/** Delegates the provided message to [Timber.d] if any trees are planted. */
inline fun logD(message: () -> String) = ifPlanted { Timber.d(message()) }

/** Delegates the provided message to [Timber.d] if any trees are planted. */
inline fun logD(throwable: Throwable, message: () -> String) = ifPlanted { Timber.d(throwable, message()) }

/** Delegates the throwable to [Timber.d]. Made for convenience when called from java */
fun logD(throwable: Throwable) = Timber.d(throwable)

/** Delegates the provided message to [Timber.v] if any trees are planted. */
inline fun logV(message: () -> String) = ifPlanted { Timber.v(message()) }

/** Delegates the provided message to [Timber.v] if any trees are planted. */
inline fun logV(throwable: Throwable, message: () -> String) = ifPlanted { Timber.v(throwable, message()) }

/** Delegates the throwable to [Timber.v]. Made for convenience when called from java */
fun logV(throwable: Throwable) = Timber.v(throwable)

/** Delegates the provided message to [Timber.wtf] if any trees are planted. */
inline fun logWtf(message: () -> String) = ifPlanted { Timber.wtf(message()) }

/** Delegates the provided message to [Timber.wtf] if any trees are planted. */
inline fun logWtf(throwable: Throwable, message: () -> String) = ifPlanted { Timber.wtf(throwable, message()) }

/** Delegates the throwable to [Timber.wtf]. Made for convenience when called from java */
fun logWtf(throwable: Throwable) = Timber.wtf(throwable)

/** Delegates the provided message to [Timber.log] if any trees are planted. */
inline fun log(priority: Int, t: Throwable, message: () -> String) = ifPlanted { Timber.log(priority, t, message()) }

/** Delegates the provided message to [Timber.log] if any trees are planted. */
inline fun log(priority: Int, message: () -> String) = ifPlanted { Timber.log(priority, message()) }

/** Delegates the throwable to [Timber.log]. Made for convenience when called from java */
fun log(priority: Int, throwable: Throwable) = Timber.log(priority, throwable)