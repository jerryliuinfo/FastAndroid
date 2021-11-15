package com.drakeet.purewriter.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.EdgeEffect

/**
 * This class performs the graphical effect used at the edges of scrollable widgets
 * when the user scrolls beyond the content bounds in 2D space.
 *
 *
 * EdgeEffect is stateful. Custom widgets using EdgeEffect should create an
 * instance for each edge that should show the effect, feed it input data using
 * the methods [.onAbsorb], [.onPull], and [.onRelease],
 * and draw the effect using [.draw] in the widget's overridden
 * [android.view.View.draw] method. If [.isFinished] returns
 * false after drawing, the edge effect's animation is not yet complete and the widget
 * should schedule another drawing pass to continue the animation.
 *
 *
 * When drawing, widgets should draw their main content and child views first,
 * usually by invoking `super.draw(canvas)` from an overridden `draw`
 * method. (This will invoke onDraw and dispatch drawing to child views as needed.)
 * The edge effect may then be drawn on top of the view's content using the
 * [.draw] method.
 *
 * @since 4.0 Ice Scream Sandwich (14)
 */
@Suppress("ReplaceJavaStaticMethodWithKotlinAnalog")
abstract class StretchEdgeEffect(context: Context, val view: View) : EdgeEffect(context) {

  var mGlowScaleY = 0f
  private var mGlowScaleYStart = 0f
  private var mGlowScaleYFinish = 0f
  private var mStartTime: Long = 0
  private var mDuration = 0f
  private val mInterpolator: Interpolator = DecelerateInterpolator()
  var mState = STATE_IDLE
  private var mPullDistance = 0f
  private val mBounds = Rect()
  private var mBaseGlowScale = 0f
  private var mDisplacement = 0.5f
  private var mTargetDisplacement = 0.5f
  protected var height = 0f

  /**
   * Set the size of this edge effect in pixels.
   *
   * @param width Effect width in pixels
   * @param height Effect height in pixels
   */
  override fun setSize(width: Int, height: Int) {
    val r = width * RADIUS_FACTOR / SIN
    val y = COS * r
    val h = r - y
    val or = height * RADIUS_FACTOR / SIN
    val oy = COS * or
    val oh = or - oy
    mBaseGlowScale = if (h > 0) Math.min(oh / h, 1f) else 1f
    mBounds[mBounds.left, mBounds.top, width] = Math.min(height.toFloat(), h).toInt()
    this.height = height.toFloat()
  }

  /**
   * Reports if this EdgeEffect's animation is finished. If this method returns false
   * after a call to [.draw] the host widget should schedule another
   * drawing pass to continue the animation.
   *
   * @return true if animation is finished, false if drawing should continue on the next frame.
   */
  override fun isFinished(): Boolean {
    return mState == STATE_IDLE
  }

  /**
   * Immediately finish the current animation.
   * After this call [.isFinished] will return true.
   */
  override fun finish() {
    mState = STATE_IDLE
  }

  /**
   * A view should call this when content is pulled away from an edge by the user.
   * This will update the state of the current visual effect and its associated animation.
   * The host view should always [android.view.View.invalidate] after this
   * and draw the results accordingly.
   *
   *
   * Views using EdgeEffect should favor [.onPull] when the displacement
   * of the pull point is known.
   *
   * @param deltaDistance Change in distance since the last call. Values may be 0 (no change) to
   * 1.f (full length of the view) or negative values to express change
   * back toward the edge reached to initiate the effect.
   */
  override fun onPull(deltaDistance: Float) {
    onPull(deltaDistance, 0.5f)
  }

  /**
   * A view should call this when content is pulled away from an edge by the user.
   * This will update the state of the current visual effect and its associated animation.
   * The host view should always {@link android.view.View#invalidate()} after this
   * and draw the results accordingly.
   *
   * @param deltaDistance Change in distance since the last call. Values may be 0 (no change) to
   *                      1.f (full length of the view) or negative values to express change
   *                      back toward the edge reached to initiate the effect.
   * @param displacement The displacement from the starting side of the effect of the point
   *                     initiating the pull. In the case of touch this is the finger position.
   *                     Values may be from 0-1.
   */
  override fun onPull(deltaDistance: Float, displacement: Float) {
    val now = AnimationUtils.currentAnimationTimeMillis()
    mTargetDisplacement = displacement
    if (mState == STATE_PULL_DECAY && now - mStartTime < mDuration) {
      return
    }
    if (mState != STATE_PULL) {
      mGlowScaleY = Math.max(PULL_GLOW_BEGIN, mGlowScaleY)
    }
    mState = STATE_PULL
    mStartTime = now
    mDuration = PULL_TIME.toFloat()
    mPullDistance += deltaDistance
    if (mPullDistance == 0f) {
      mGlowScaleYStart = 0f
      mGlowScaleY = mGlowScaleYStart
    } else {
      val scale = (Math.max(0.0, 1 - 1 / Math.sqrt((Math.abs(mPullDistance) * mBounds.height()).toDouble()) - 0.3) / 0.7).toFloat()
      mGlowScaleYStart = scale
      mGlowScaleY = mGlowScaleYStart
    }
    mGlowScaleYFinish = mGlowScaleY
  }

  /**
   * Call when the object is released after being pulled.
   * This will begin the "decay" phase of the effect. After calling this method
   * the host view should [android.view.View.invalidate] and thereby
   * draw the results accordingly.
   */
  override fun onRelease() {
    mPullDistance = 0f
    if (mState != STATE_PULL && mState != STATE_PULL_DECAY) {
      return
    }
    mState = STATE_RECEDE
    mGlowScaleYStart = mGlowScaleY
    mGlowScaleYFinish = 0f
    mStartTime = AnimationUtils.currentAnimationTimeMillis()
    mDuration = RECEDE_TIME.toFloat()
  }

  /**
   * Call when the effect absorbs an impact at the given velocity.
   * Used when a fling reaches the scroll boundary.
   *
   *
   * When using a [Scroller] or [OverScroller],
   * the method `getCurrVelocity` will provide a reasonable approximation
   * to use here.
   *
   * @param v Velocity at impact in pixels per second.
   */
  override fun onAbsorb(v: Int) {
    var velocity = v
    mState = STATE_ABSORB
    velocity = Math.min(Math.max(MIN_VELOCITY, Math.abs(velocity)), MAX_VELOCITY)
    mStartTime = AnimationUtils.currentAnimationTimeMillis()
    mDuration = 0.15f + velocity * 0.02f
    // The glow depends more on the velocity, and therefore starts out
    // nearly invisible.
    mGlowScaleYStart = Math.max(mGlowScaleY, 0f)
    // Growth for the size of the glow should be quadratic to properly
    // respond
    // to a user's scrolling speed. The faster the scrolling speed, the more
    // intense the effect should be for both the size and the saturation.
    mGlowScaleYFinish = Math.min(0.025f + velocity * (velocity / 100) * 0.00015f / 2, 1f)
    mTargetDisplacement = 0.5f
  }

  // Drakeet: 如果这个 EdgeEffect 是头部的，就返回底部的 EdgeEffect，反之返回头部的 EdgeEffect
  abstract fun another(): StretchEdgeEffect

  // Drakeet: 如果这个 EdgeEffect 是头部的，就返回 0f，反之返回 height
  abstract fun pivotY(): Float

  /**
   * Draw into the provided canvas. Assumes that the canvas has been rotated
   * accordingly and the size has been set. The effect will be drawn the full
   * width of X=0 to X=width, beginning from Y=0 and extending to some factor <
   * 1.f of height.
   *
   * @param canvas Canvas to draw into
   * @return true if drawing should continue beyond this frame to continue the
   * animation
   */
  override fun draw(canvas: Canvas): Boolean {
    if (another().mGlowScaleY != 0f) {
      mGlowScaleY = 0f
      mGlowScaleYStart = 0f
      mGlowScaleYFinish = 0f
      mState = STATE_IDLE
      // return false
    }
    update()
    val scaleY = (1 + mGlowScaleY.coerceAtMost(1.0f) * mBaseGlowScale * 0.0382f)
    view.pivotY = pivotY()
    view.scaleY = scaleY
    var oneLastFrame = false
    if (mState == STATE_RECEDE && mGlowScaleY == 0f) {
      mState = STATE_IDLE
      oneLastFrame = true
    }
    return mState != STATE_IDLE || oneLastFrame
  }

  /**
   * Return the maximum height that the edge effect will be drawn at given the original
   * [input size][.setSize].
   *
   * @return The maximum height of the edge effect
   * @since 5.0 Lollipop (21)
   */
  override fun getMaxHeight(): Int {
    return (mBounds.height() * MAX_GLOW_SCALE + 0.5f).toInt()
  }

  private fun update() {
    val time = AnimationUtils.currentAnimationTimeMillis()
    val t = Math.min((time - mStartTime) / mDuration, 1f)
    val interpolation = mInterpolator.getInterpolation(t)
    mGlowScaleY = mGlowScaleYStart + (mGlowScaleYFinish - mGlowScaleYStart) * interpolation
    mDisplacement = (mDisplacement + mTargetDisplacement) / 2
    if (t >= 1f - EPSILON) {
      when (mState) {
        STATE_ABSORB -> {
          mState = STATE_RECEDE
          mStartTime = AnimationUtils.currentAnimationTimeMillis()
          mDuration = RECEDE_TIME.toFloat()
          mGlowScaleYStart = mGlowScaleY
          mGlowScaleYFinish = 0f
        }
        STATE_PULL -> {
          mState = STATE_PULL_DECAY
          mStartTime = AnimationUtils.currentAnimationTimeMillis()
          mDuration = PULL_DECAY_TIME.toFloat()
          mGlowScaleYStart = mGlowScaleY
          mGlowScaleYFinish = 0f
        }
        STATE_PULL_DECAY -> mState = STATE_RECEDE
        STATE_RECEDE -> mState = STATE_IDLE
      }
    }
  }

  companion object {
    // Time it will take the effect to fully recede in ms
    private const val RECEDE_TIME = 480

    // Time it will take before a pulled glow begins receding in ms
    private const val PULL_TIME = 167

    // Time it will take in ms for a pulled glow to decay to partial strength before release
    private const val PULL_DECAY_TIME = 2000
    private const val MAX_GLOW_SCALE = 2f
    private const val PULL_GLOW_BEGIN = 0f

    // Minimum velocity that will be absorbed
    private const val MIN_VELOCITY = 100

    // Maximum velocity, clamps at this value
    private const val MAX_VELOCITY = 10000
    private const val EPSILON = 0.001f
    private const val ANGLE = Math.PI / 6
    private val SIN = Math.sin(ANGLE).toFloat()
    private val COS = Math.cos(ANGLE).toFloat()
    private const val RADIUS_FACTOR = 0.6f
    private const val STATE_IDLE = 0
    private const val STATE_PULL = 1
    private const val STATE_ABSORB = 2
    private const val STATE_RECEDE = 3
    private const val STATE_PULL_DECAY = 4
  }
}
