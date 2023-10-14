package com.tesla.framework.component.debounce

import android.os.SystemClock
import android.view.View

/**
 * Created by Jerry on 2023/5/14.
 */
fun View.setOnDebouncedClickListener(action: (View) -> Unit) {
    val actionDebouncer = ActionDebouncer(this,action)

    // This is the only place in the project where we should actually use setOnClickListener
    setOnClickListener {
        actionDebouncer.notifyAction()
    }
}

fun View.removeOnDebouncedClickListener() {
    setOnClickListener(null)
    isClickable = false
}

private class ActionDebouncer(private val view: View,private val action: (View) -> Unit) {

    private var lastActionTime = 0L

    fun notifyAction() {
        val now = SystemClock.elapsedRealtime()

        val millisecondsPassed = now - lastActionTime
        val actionAllowed = millisecondsPassed > DEBOUNCE_INTERVAL_MILLISECONDS
        lastActionTime = now

        if (actionAllowed) {
            action.invoke(view)
        }
    }

    companion object {
        const val DEBOUNCE_INTERVAL_MILLISECONDS = 600L
    }
}