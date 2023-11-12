package com.apache.fastandroid.demo.drakeet.insets

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsCompat

class EdgeInsetDelegate(private val activity: Activity) {

  private var everGivenInsetsToDecorView = false

  fun start() {
    WindowCompat.setDecorFitsSystemWindows(activity.window, false)
    everGivenInsetsToDecorView = false
    activity.window.decorView.doOnApplyWindowInsets { windowInsets, _, _ ->
      val navigationBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.navigationBars())
      val isGestureNavigation = isGestureNavigation(navigationBarsInsets)
      when {
        isGestureNavigation -> {
          // Let decorView draw the translucent navigationBarColor
          ViewCompat.onApplyWindowInsets(activity.window.decorView, windowInsets)
          everGivenInsetsToDecorView = true
        }
        isGestureNavigation && everGivenInsetsToDecorView -> {
          // Let DecorView remove navigationBarColor once it has bean drawn
          val noBottomInsets = WindowInsetsCompat.Builder()
            .setInsets(
              WindowInsetsCompat.Type.navigationBars(),
              Insets.of(
                navigationBarsInsets.left,
                navigationBarsInsets.top,
                navigationBarsInsets.right,
                0
              )
            )
            .build()
          ViewCompat.onApplyWindowInsets(activity.window.decorView, noBottomInsets)
        }
      }
    }
  }

  private fun isGestureNavigation(navigationBarsInsets: Insets): Boolean {
    val threshold = (20 * activity.resources.displayMetrics.density).toInt()
    // 44 is the fixed height of the iOS-like navigation bar (Gesture navigation), in pixels!
    return navigationBarsInsets.bottom <= threshold.coerceAtLeast(44) // 20dp or 44px
  }

  fun smoothSoftKeyboardTransition(rootView: View) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      ViewCompat.setWindowInsetsAnimationCallback(rootView,
        object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP) {

          private var isImeVisible = false

          override fun onPrepare(animation: WindowInsetsAnimationCompat) {
            super.onPrepare(animation)
            isImeVisible = ViewCompat.getRootWindowInsets(rootView)?.isVisible(WindowInsetsCompat.Type.ime()) ?: false
          }

          override fun onProgress(
            insets: WindowInsetsCompat,
            runningAnimations: MutableList<WindowInsetsAnimationCompat>
          ): WindowInsetsCompat {
            val typesInset = insets.getInsets(WindowInsetsCompat.Type.ime())
            if (!isImeVisible) {
              // fooView.translationY = fooView.height - typesInset.bottom + ...
            }
            return insets
          }

          override fun onEnd(animation: WindowInsetsAnimationCompat) {
            super.onEnd(animation)
            // fooView.translationY = 0f
          }
        })
    }
  }
}