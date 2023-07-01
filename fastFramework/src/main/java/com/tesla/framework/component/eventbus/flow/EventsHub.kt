package com.tesla.framework.component.eventbus.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

interface Event

class EventHub @Inject constructor() {

    private val sharedEventFlow: MutableSharedFlow<Event> = MutableSharedFlow()
    val events: Flow<Event> = sharedEventFlow

    suspend fun dispatch(event: Event) {
        sharedEventFlow.emit(event)
    }
}
