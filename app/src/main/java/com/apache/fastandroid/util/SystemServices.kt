package com.apache.fastandroid.util

import android.content.Context
import android.content.pm.PackageManager
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import com.blankj.utilcode.util.Utils

object SystemServices {
  val packageManager: PackageManager by lazy { Utils.getApp().packageManager }
  val inputMethodManager: InputMethodManager by lazy { Utils.getApp().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
  val windowManager: WindowManager by lazy { Utils.getApp().getSystemService(Context.WINDOW_SERVICE) as WindowManager }
}
