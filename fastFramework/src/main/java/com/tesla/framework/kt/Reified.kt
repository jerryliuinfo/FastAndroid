package com.tesla.framework.kt

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.google.gson.Gson
import com.google.gson.JsonElement

/**
 * Created with Android Studio.
 * Description:
 * @author: Wangjianxian
 * @CreateDate: 2020/5/1 16:34
 */

inline fun <reified T:Activity> launchActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T> launchActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java).apply(block)
    context.startActivity(intent)
}

inline fun <reified T:Any> Gson.fromJson(json:JsonElement) = this.fromJson(json,T::class.java)