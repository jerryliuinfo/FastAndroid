package com.apache.fastandroid.demo.extension

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IntRange
import java.io.Closeable
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.math.ceil
import kotlin.math.min

/**
 * Created by Jerry on 2022/10/18.
 */
class AppExtensions {
}


fun String?.isNotNullOrEmpty(): Boolean {
    if (this == null || this == "null") {
        return false
    }
    return !isNullOrEmpty()
}

fun EditText.setTextAndMaintainSelection(text: CharSequence?) {
    if (text == null) {
        setText("")
        return
    }

    val formerStart = min(selectionStart, text.length)
    val formerEnd = min(selectionEnd, text.length)
    setText(text)
    if (formerEnd <= formerStart) {
        setSelection(formerStart)
    } else {
        setSelection(formerStart, formerEnd)
    }
}

fun EditText.onTextChanged(
    @IntRange(from = 0, to = 10000) debounce: Int = 0,
    cb: (String) -> Unit
) {
    addTextChangedListener(object : TextWatcher {
        val callbackRunner = Runnable {
            cb(text.trim().toString())
        }

        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(
            s: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) = Unit

        override fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            removeCallbacks(callbackRunner)
            if (debounce == 0) {
                callbackRunner.run()
            } else {
                postDelayed(callbackRunner, debounce.toLong())
            }
        }
    })
}


const val SECOND: Long = 1000
const val MINUTE = SECOND * 60
const val HOUR = MINUTE * 60
const val DAY = HOUR * 24
const val WEEK = DAY * 7
const val MONTH = WEEK * 4

fun Long.timeString() = when {
    this <= 0 -> "??"
    this >= MONTH ->
        "${ceil((this.toFloat() / MONTH.toFloat()).toDouble()).toInt()}mo"
    this >= WEEK ->
        "${ceil((this.toFloat() / WEEK.toFloat()).toDouble()).toInt()}w"
    this >= DAY ->
        "${ceil((this.toFloat() / DAY.toFloat()).toDouble()).toInt()}d"
    this >= HOUR ->
        "${ceil((this.toFloat() / HOUR.toFloat()).toDouble()).toInt()}h"
    this >= MINUTE -> {
        val result = "${ceil((this.toFloat() / MINUTE.toFloat()).toDouble()).toInt()}m"
        if (result == "60m") {
            "1h"
        } else {
            result
        }
    }
    else -> "<1m"
}


inline fun <reified T> Context.systemService(name: String): T? {
    return getSystemService(name) as? T
}

private var toast: Toast? = null

/** Shows a toast in the receiving context, cancelling any previous. */
fun Context.toast(message: Int) {
    toast?.cancel()
    toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
        .apply {
            show()
        }
}


inline fun <T : Closeable?, R> T.myUse(block: (T) -> R): R {
    return block(this)
}

@SinceKotlin("1.1")
inline fun <T> T.myTakeIf(predicate: (T) -> Boolean): T? {

    return if (predicate(this)) this else null
}

/**
 * Returns `this` value if it _does not_ satisfy the given [predicate] or `null`, if it does.
 *
 * For detailed usage information see the documentation for [scope functions](https://kotlinlang.org/docs/reference/scope-functions.html#takeif-and-takeunless).
 */
@SinceKotlin("1.1")
inline fun <T> T.myTakeUnless(predicate: (T) -> Boolean): T? {
    return if (!predicate(this)) this else null
}

inline fun <T> Iterable<T>.myCount(predicate: (T) -> Boolean): Int {
    if (this is Collection && isEmpty()) return 0
    var count = 0
    for (element in this) if (predicate(element)) (++count)
    return count
}

inline fun <T, K> Iterable<T>.myDistinctBy(selector: (T) -> K): List<T> {
    val set = HashSet<K>()
    val list = ArrayList<T>()
    for (e in this) {
        val key = selector(e)
        if (set.add(key))
            list.add(e)
    }
    return list
}

inline fun <T> Iterable<T>.myForEachIndexed(action: (index: Int, T) -> Unit): Unit {
    var index = 0
    for (item in this) action((index++), item)
}

inline fun <T> Iterable<T>.myMinOf(selector: (T) -> Double): Double {
    val iterator = iterator()
    if (!iterator.hasNext()) throw NoSuchElementException()
    var minValue = selector(iterator.next())
    while (iterator.hasNext()) {
        val v = selector(iterator.next())
        minValue = minOf(minValue, v)
    }
    return minValue
}