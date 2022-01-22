package com.tesla.framework.component.livedata

/**
 * Created by Jerry on 2022/1/22.
 */
class Event <out T>(private val content:T) {

     var hasBeenHandled = false
        private set

    fun getContentIfHandled(): T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled = true
            content
        }
    }

    fun peekContent():T = content
}