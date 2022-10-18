package com.apache.fastandroid.demo.extension

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.IntRange
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
