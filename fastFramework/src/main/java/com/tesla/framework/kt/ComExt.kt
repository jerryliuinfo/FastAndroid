package com.tesla.framework.kt

import androidx.databinding.ObservableInt

/**
 * Created by Jerry on 2022/4/20.
 */


fun ObservableInt.increment(){
    set(this.get() + 1)
}

infix fun Int.times(str:String) = str.repeat(this)

infix fun String.onto(other:String) = Pair(this,other)


fun <T : Any> Iterable<T>.maxAge(lastModified: (T) -> Long): Long {
    var result = 0L
    forEach {
        result = maxOf(result, lastModified(it))
    }
    return result
}

/**
 * Faster lazy delegation for Android.
 * Warning: Only use for objects accessed on main thread
 */
fun <T> lazyAndroid(initializer: () -> T): Lazy<T> {
    return lazy(LazyThreadSafetyMode.NONE, initializer)
}