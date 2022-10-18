package com.tesla.framework.component.livedata

import androidx.lifecycle.Observer

/**
 * Created by Jerry on 2022/1/22.
 * 只会回调一次的 LiveData
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
