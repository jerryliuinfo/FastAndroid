package com.tesla.framework.kt

import android.content.Context
import android.content.Intent

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/5/1 16:34
 */

inline fun <reified T> launchActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T> launchActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}