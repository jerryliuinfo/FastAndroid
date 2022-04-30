package com.apache.fastandroid.demo.drakeet.customview.sample.chat

import android.os.CancellationSignal
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsAnimationControlListenerCompat
import androidx.core.view.WindowInsetsAnimationControllerCompat
import androidx.core.view.WindowInsetsCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.dynamicanimation.animation.springAnimationOf
import androidx.dynamicanimation.animation.withSpringForceProperties
import kotlin.math.roundToInt

private const val SCROLL_THRESHOLD = 0.15f

private val linearInterpolator = LinearInterpolator()

class SimpleImeAnimationController {

  private var insetsAnimationController: WindowInsetsAnimationControllerCompat? = null
  private var pendingRequestCancellationSignal: CancellationSignal? = null
  private var pendingRequestOnReady: ((WindowInsetsAnimationControllerCompat) -> Unit)? = null

  private val animationControlListener by lazy {
    object : WindowInsetsAnimationControlListenerCompat {
      override fun onReady(controller: WindowInsetsAnimationControllerCompat, types: Int) {
        onRequestReady(controller)
      }

      override fun onFinished(controller: WindowInsetsAnimationControllerCompat) = reset()

      override fun onCancelled(controller: WindowInsetsAnimationControllerCompat?) = reset()
    }
  }

  private var isImeShownAtStart = false
  private var currentSpringAnimation: SpringAnimation? = null

  fun startControlRequest(
    view: View,
    onRequestReady: ((WindowInsetsAnimationControllerCompat) -> Unit)? = null
  ) {
    isImeShownAtStart = ViewCompat.getRootWindowInsets(view)
      ?.isVisible(WindowInsetsCompat.Type.ime()) == true

    pendingRequestCancellationSignal = CancellationSignal()
    pendingRequestOnReady = onRequestReady

    ViewCompat.getWindowInsetsController(view)?.controlWindowInsetsAnimation(
      WindowInsetsCompat.Type.ime(),
      -1,
      linearInterpolator,
      pendingRequestCancellationSignal,
      animationControlListener
    )
  }

  fun cancel() {
    insetsAnimationController?.finish(isImeShownAtStart)
    pendingRequestCancellationSignal?.cancel()
    currentSpringAnimation?.cancel()
    reset()
  }

  fun isInsetAnimationInProgress(): Boolean {
    return insetsAnimationController != null
  }

  fun isInsetAnimationFinishing(): Boolean {
    return currentSpringAnimation != null
  }

  fun isInsetAnimationRequestPending(): Boolean {
    return pendingRequestCancellationSignal != null
  }

  fun startAndFling(view: View, velocityY: Float) = startControlRequest(view) {
    animateToFinish(velocityY)
  }

  fun animateToFinish(velocityY: Float? = null) {
    val controller = insetsAnimationController
    if (controller == null) {
      pendingRequestCancellationSignal?.cancel()
      return
    }

    val current = controller.currentInsets.bottom
    val shown = controller.shownStateInsets.bottom
    val hidden = controller.hiddenStateInsets.bottom

    when {
      velocityY != null -> animateImeToVisibility(velocityY > 0, velocityY)
      current == shown -> controller.finish(true)
      current == hidden -> controller.finish(false)
      else -> {
        if (controller.currentFraction >= SCROLL_THRESHOLD) {
          animateImeToVisibility(!isImeShownAtStart)
        } else {
          animateImeToVisibility(isImeShownAtStart)
        }
      }
    }
  }

  private fun animateImeToVisibility(visible: Boolean, velocityY: Float? = null) {
    val controller = insetsAnimationController
      ?: throw IllegalStateException("Controller should not be null")

    currentSpringAnimation = springAnimationOf(
      setter = { insetTo(it.roundToInt()) },
      getter = { controller.currentInsets.bottom.toFloat() },
      finalPosition = when {
        visible -> controller.shownStateInsets.bottom.toFloat()
        else -> controller.hiddenStateInsets.bottom.toFloat()
      }
    ).withSpringForceProperties {
      dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
      stiffness = SpringForce.STIFFNESS_MEDIUM
    }.apply {
      if (velocityY != null) {
        setStartVelocity(velocityY)
      }
      addEndListener { animation, _, _, _ ->
        if (animation == currentSpringAnimation) {
          currentSpringAnimation = null
        }
        finish()
      }
    }.also { it.start() }
  }

  fun insetBy(dy: Int): Int {
    val controller = insetsAnimationController
      ?: throw IllegalStateException(
        "Current WindowInsetsAnimationController is null." +
          "This should only be called if isAnimationInProgress() returns true"
      )
    return insetTo(controller.currentInsets.bottom - dy)
  }

  fun insetTo(inset: Int): Int {
    val controller = insetsAnimationController
      ?: throw IllegalStateException(
        "Current WindowInsetsAnimationController is null." +
          "This should only be called if isAnimationInProgress() returns true"
      )

    val hiddenBottom = controller.hiddenStateInsets.bottom
    val shownBottom = controller.shownStateInsets.bottom
    val startBottom = if (isImeShownAtStart) shownBottom else hiddenBottom
    val endBottom = if (isImeShownAtStart) hiddenBottom else shownBottom

    val coercedBottom = inset.coerceIn(hiddenBottom, shownBottom)
    val consumedDy = controller.currentInsets.bottom - coercedBottom

    controller.setInsetsAndAlpha(
      Insets.of(0, 0, 0, coercedBottom),
      1f,
      (coercedBottom - startBottom) / (endBottom - startBottom).toFloat()
    )
    return consumedDy
  }

  private fun finish() {
    val controller = insetsAnimationController
    if (controller == null) {
      pendingRequestCancellationSignal?.cancel()
      return
    }

    val current = controller.currentInsets.bottom
    val shown = controller.shownStateInsets.bottom
    val hidden = controller.hiddenStateInsets.bottom

    when (current) {
      shown -> controller.finish(true)
      hidden -> controller.finish(false)
      else -> {
        if (controller.currentFraction >= SCROLL_THRESHOLD) {
          controller.finish(!isImeShownAtStart)
        } else {
          controller.finish(isImeShownAtStart)
        }
      }
    }
  }

  private fun onRequestReady(controller: WindowInsetsAnimationControllerCompat) {
    pendingRequestCancellationSignal = null
    insetsAnimationController = controller
    pendingRequestOnReady?.invoke(controller)
    pendingRequestOnReady = null
  }

  private fun reset() {
    pendingRequestCancellationSignal = null
    insetsAnimationController = null
    pendingRequestOnReady = null
    isImeShownAtStart = false
    currentSpringAnimation?.cancel()
    currentSpringAnimation = null
  }
}
