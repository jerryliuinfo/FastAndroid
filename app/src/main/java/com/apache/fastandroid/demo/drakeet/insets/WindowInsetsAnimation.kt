package com.apache.fastandroid.demo.drakeet.insets

import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.WindowInsetsAnimationCompat.Callback.DISPATCH_MODE_STOP
import androidx.core.view.WindowInsetsCompat

fun View.doOnApplyWindowInsets(
  deferredInsetTypes: Int = WindowInsetsCompat.Type.ime(),
  block: (windowInsets: WindowInsetsCompat, padding: InitialPadding, margin: InitialMargin, deferredInsets: Boolean) -> Unit,
) {
    val initialPadding = recordInitialPaddingForView()
    val initialMargin = recordInitialMarginForView()

    val callback = object : WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_CONTINUE_ON_SUBTREE),
        OnApplyWindowInsetsListener {

        private var deferredInsets = false
        private var lastWindowInsets: WindowInsetsCompat? = null

        override fun onApplyWindowInsets(
          v: View,
          windowInsets: WindowInsetsCompat,
        ): WindowInsetsCompat {
            lastWindowInsets = windowInsets
            block(windowInsets, initialPadding, initialMargin, deferredInsets)
            return windowInsets
        }

        override fun onPrepare(animation: WindowInsetsAnimationCompat) {
            if (animation.typeMask and deferredInsetTypes != 0) {
                deferredInsets = true
            }
        }

        override fun onEnd(animation: WindowInsetsAnimationCompat) {
            if (deferredInsets && (animation.typeMask and deferredInsetTypes) != 0) {
                deferredInsets = false
                lastWindowInsets?.let { windowInsets ->
                    ViewCompat.dispatchApplyWindowInsets(this@doOnApplyWindowInsets, windowInsets)
                }
            }
        }

        override fun onProgress(
          insets: WindowInsetsCompat,
          runningAnimations: MutableList<WindowInsetsAnimationCompat>,
        ): WindowInsetsCompat {
            return insets
        }
    }

    ViewCompat.setWindowInsetsAnimationCallback(this, callback)
    ViewCompat.setOnApplyWindowInsetsListener(this, callback)

    requestApplyInsetsWhenAttached()
}

@Suppress("NOTHING_TO_INLINE")
inline fun ViewGroup.translateDeferringInsetsAnimation(
  dispatchMode: Int = DISPATCH_MODE_STOP,
) {
    clipToPadding = false
    (this as View).translateDeferringInsetsAnimation(dispatchMode)
}

fun View.translateDeferringInsetsAnimation(
  dispatchMode: Int = DISPATCH_MODE_STOP,
) {
    val callback = object : WindowInsetsAnimationCompat.Callback(dispatchMode) {
        override fun onProgress(
          insets: WindowInsetsCompat,
          runningAnimations: MutableList<WindowInsetsAnimationCompat>,
        ): WindowInsetsCompat {
            val imeInset = insets.getInsets(WindowInsetsCompat.Type.ime())
            val systemInset = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val diff = Insets.subtract(imeInset, systemInset).let {
                Insets.max(it, Insets.NONE)
            }

            translationX = (diff.left - diff.right).toFloat()
            translationY = (diff.top - diff.bottom).toFloat()
            return insets
        }

        override fun onEnd(animation: WindowInsetsAnimationCompat) {
            super.onEnd(animation)
            translationX = 0f
            translationY = 0f
        }
    }
    ViewCompat.setWindowInsetsAnimationCallback(this, callback)
}
