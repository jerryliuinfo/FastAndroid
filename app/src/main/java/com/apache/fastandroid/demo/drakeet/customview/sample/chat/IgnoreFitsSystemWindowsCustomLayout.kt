package com.apache.fastandroid.demo.drakeet.customview.sample.chat

import android.content.Context
import android.util.AttributeSet
import android.view.WindowInsets
import com.seiko.demo.base.CustomLayout

abstract class IgnoreFitsSystemWindowsCustomLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null
) : CustomLayout(context, attrs) {
  init {
    fitsSystemWindows = true
  }

  override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets {
    return insets
  }
}