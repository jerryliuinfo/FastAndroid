package com.tesla.framework.component.messagelist


import kotlinx.coroutines.CoroutineScope

class MessageListLiveDataFactory(
    private val messageListLoader: MessageListLoader,
    private val messageListRepository: MessageListRepository,
) {
    fun create(coroutineScope: CoroutineScope): MessageListLiveData {
        return MessageListLiveData(messageListLoader, messageListRepository, coroutineScope)
    }
}
