package com.tesla.framework.component.livedata

import androidx.lifecycle.Observer

/**
 * Created by Jerry on 2022/1/22.
 */
open class Event <out T>(private val content:T) {

     var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent():T = content
}


/**
 * An [Observer] for [Event]s, simplifying the pattern of checking if the [Event]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [Event]'s contents has not been handled.
 */
class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}