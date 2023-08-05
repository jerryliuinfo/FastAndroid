package com.tesla.framework.kt

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.core.os.bundleOf
import kotlin.reflect.KProperty1

fun Context.toTheme(@StyleRes styleId: Int): Context {
  return ContextThemeWrapper(this, styleId)
}

fun Context.getAttrColor(@AttrRes attrId: Int): Int {
  return obtainStyledAttributes(intArrayOf(attrId)).use {
    val colorId = it.getResourceId(0, 0)
    ContextCompat.getColor(this, colorId)
  }
}


inline fun <reified T : Activity> Context.startActivity() {
  startActivity(Intent(this, T::class.java))
}


inline fun <reified T : AppCompatActivity> Context.startActivity(
  vararg params: Pair<KProperty1<out Any?, Any?>, Any?>,
) {
  val extras = params.map { it.first.name to it.second }.toTypedArray()
  val intent = Intent(this, T::class.java)
  @Suppress("detekt.SpreadOperator")
  intent.putExtras(bundleOf(*extras))
  startActivity(intent)
}

inline fun <reified T: Activity> launchActivity(context: Context) {
  val intent = Intent(context, T::class.java)
  context.startActivity(intent)
}


inline fun <reified T> launchActivity(context: Context, block: Intent.() -> Unit) {
  val intent = Intent(context, T::class.java).apply(block)
  context.startActivity(intent)
}

