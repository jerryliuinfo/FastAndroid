package com.tesla.framework.kt

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.JsonElement



inline fun <reified T:Activity> launchActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T> launchActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java).apply(block)
    context.startActivity(intent)
}

inline fun <reified T : Activity> Context.startActivity() {
    startActivity(Intent(this, T::class.java))
}


inline fun <reified T:Any> Gson.fromJson(json:JsonElement) = this.fromJson(json,T::class.java)