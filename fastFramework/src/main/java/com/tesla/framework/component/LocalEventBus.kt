package com.tesla.framework.component

import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * author : 王星星
 * date : 2021/7/22 20:04
 * email : 1099420259@qq.com
 * description :
 */
object LocalEventBus {

    val events = MutableSharedFlow<Event>()

    suspend fun postEvent(event: Event) {
        events.emit(event)
    }
}

data class Event(val timestamp: Long)