package com.apache.fastandroid.kt

import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import com.apache.fastandroid.sample.IntDefDemo
import kotlin.math.pow

val Float.dp
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )


val Int.dp
    get() = this.toFloat().dp


val Float.sp
    get() = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            Resources.getSystem().displayMetrics
    )

fun Float.powWrapp(n: Int): Float = this.pow(n)

fun Int.getName(): String {
    return when (this) {
        1 -> "AAA"
        2 -> "BBB"
        3 -> "CCC"
        else -> "AAA"
    }
}

fun Int.getValue(): String = kotlin.run {
    when (this) {
        1 -> "AAA"
        2 -> "BBB"
        3 -> "CCC"
        else -> "AAA"
    }
}

fun View.gone() {
    if (visibility == View.VISIBLE) {
        visibility = View.GONE
    }
}

//扩展函数，view显示
fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}