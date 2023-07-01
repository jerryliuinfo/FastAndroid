package com.apache.fastandroid.events

import com.tesla.framework.component.eventbus.flow.Event

/**
 * Created by Jerry on 2023/6/25.
 */
data class FavoriteEvent(val statusId: String) : Event
