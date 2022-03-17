package com.tesla.framework.kt

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * author: jerry
 * created on: 2020/10/13 11:15 AM
 * description:
 */


/**
 * 递归地把控件的enable 状态设置为指定值
 */
fun View?.setEnableStateRecursively(enable: Boolean) {
    if (this == null) return
    if (this is ViewGroup) {
        // 递归子控件
//        this.forEach {
//            it.setEnableStateRecursively(enable)
//        }

        for (index in 1..childCount) {

        }


    }

    // 处理当前控件
    this.isEnabled = enable
}

private val sUiThreadHandler = Handler(Looper.getMainLooper())

/**
 * 在主线程中运行代码
 */
fun runOnUiThread(action: () -> Unit) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        action.invoke()
    } else {
        sUiThreadHandler.post(action)
    }
}

fun runOnUiThreadDelay(action: () -> Unit,dealy:Long) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
        sUiThreadHandler.postDelayed({
               action.invoke()
        },dealy)


    } else {
        sUiThreadHandler.postDelayed(action,dealy)
    }
}

/**
 * 在子线程中运行代码
 */
fun runOnBackground(action: () -> Unit) {
}

fun removeAllMessageOnUIThreadQueue() {
    sUiThreadHandler.removeCallbacksAndMessages(null)
}

/**
 * View是否展示在界面上
 */
fun View.isDisplayed(): Boolean {
    return getGlobalVisibleRect(Rect()) && withEffectiveVisibility(this)
}

/**
 * 是否有效展示
 */
fun withEffectiveVisibility(view: View): Boolean {
    return if (view.visibility != View.VISIBLE) {
        false
    } else {
        if (view.parent is View) {
            withEffectiveVisibility(view.parent as View)
        } else {
            true
        }
    }
}

inline fun View.removeSelf(): Boolean {
    if (this.parent != null && this.parent is ViewGroup) {
        (this.parent as ViewGroup).removeView(this)
        return true
    }
    return false
}


fun View.gone() {
    if (visibility == View.VISIBLE) {
        visibility = View.GONE
    }
}

//扩展函数，view显示
fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun RecyclerView.removeDecorations(){
    for (i in 0 until itemDecorationCount){
        removeItemDecorationAt(i)
    }
}


operator fun ViewGroup.plusAssign(other:View) = addView(other)


suspend fun View.awaitNextLayout() = suspendCancellableCoroutine<Unit> { cont ->

    // 这里的 lambda 表达式会被立即调用，允许我们创建一个监听器
    val listener = object : View.OnLayoutChangeListener {

        override fun onLayoutChange(
            v: View?,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            // 视图的下一次布局任务被调用
            // 先移除监听，防止协程泄漏
            removeOnLayoutChangeListener(this)
            // 最终，唤醒协程，恢复执行
            cont.resume(Unit,null)
        }
    }
    // 如果协程被取消，移除该监听
    cont.invokeOnCancellation { removeOnLayoutChangeListener(listener) }
    // 最终，将监听添加到 view 上
    addOnLayoutChangeListener(listener)

    // 这样协程就被挂起了，除非监听器中的 cont.resume() 方法被调用

}


suspend fun Animator.awaitEnd() = suspendCancellableCoroutine<Unit> { cont ->

    // 增加一个处理协程取消的监听器，如果协程被取消，
    // 同时执行动画监听器的 onAnimationCancel() 方法，取消动画
    cont.invokeOnCancellation { cancel() }

    addListener(object : AnimatorListenerAdapter() {
        private var endedSuccessfully = true

        override fun onAnimationCancel(animation: Animator) {
            // 动画已经被取消，修改是否成功结束的标志
            endedSuccessfully = false
        }

        override fun onAnimationEnd(animation: Animator) {

            // 为了在协程恢复后的不发生泄漏，需要确保移除监听
            animation.removeListener(this)
            if (cont.isActive) {

                // 如果协程仍处于活跃状态
                if (endedSuccessfully) {
                    // 并且动画正常结束，恢复协程
                    cont.resume(Unit,null)
                } else {
                    // 否则动画被取消，同时取消协程
                    cont.cancel()
                }
            }
        }
    })
}


fun RecyclerView.clearDecorations() {
    if (itemDecorationCount > 0) {
        for (i in itemDecorationCount - 1 downTo 0) {
            removeItemDecorationAt(i)
        }
    }
}

fun View.setBackground(drawable:Drawable){
    ViewCompat.setBackground(this,drawable )
}