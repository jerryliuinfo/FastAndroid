package com.apache.fastandroid.bean

import com.apache.fastandroid.network.model.ErrorHolder

sealed class WebsocketEvent {

    object OnClosed: WebsocketEvent()
    data class OnOpen(val result:Boolean): WebsocketEvent()
    data class OnMessage(val text: String): WebsocketEvent()
    data class OnFailure(val error: ErrorHolder): WebsocketEvent()
}