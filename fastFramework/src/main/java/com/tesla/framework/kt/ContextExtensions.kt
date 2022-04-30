package com.tesla.framework.kt

import android.content.Context
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.content.ContextCompat
import androidx.core.content.res.use

fun Context.toTheme(@StyleRes styleId: Int): Context {
  return ContextThemeWrapper(this, styleId)
}

fun Context.getAttrColor(@AttrRes attrId: Int): Int {
  return obtainStyledAttributes(intArrayOf(attrId)).use {
    val colorId = it.getResourceId(0, 0)
    ContextCompat.getColor(this, colorId)
  }
}