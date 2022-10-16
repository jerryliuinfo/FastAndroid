package com.apache.fastandroid.demo.kt.inline

import android.content.SharedPreferences
import com.apache.fastandroid.demo.bean.UserBean

/**
 * Created by Jerry on 2022/2/19.
 */
class InlineDemo {
}

fun onlyIf(debug: Boolean, block: () -> Unit) {
    if (debug) {
        block()
    }
}

inline fun onlyIf2(debug: Boolean, block: () -> Unit) {
    if (debug) {
        block()
    }
}

fun nonInlineUsage() {
    onlyIf(true) {
        println("no inline 打印日志")
    }
}

inline fun SharedPreferences.edit(
    commit: Boolean = false,
    action: SharedPreferences.Editor.() -> Unit
) {
    val editor = edit()
    action(editor)
    if (commit) {
        editor.commit()
    } else {
        editor.apply()
    }
}

fun myFunction(importantAction: Int.() -> Unit) {
    importantAction(-1)
}

inline fun SharedPreferences.edit2(
    commit: Boolean = false, noinline importantAction: Int.() -> Unit = {},
    action: SharedPreferences.Editor.() -> Unit
) {
    myFunction(importantAction)
}


inline fun costTimeMillis(block:UserBean.() -> Unit): Long {
    val startTime = System.currentTimeMillis()
    block(UserBean(name = "jerry"))
    return System.currentTimeMillis() - startTime
}