package com.tesla.framework.component.messagelist

import java.util.concurrent.CopyOnWriteArraySet

class MessageListRepository(
) {
    private val globalListeners = CopyOnWriteArraySet<MessageListChangedListener>()
    private val accountListeners = CopyOnWriteArraySet<Pair<String, MessageListChangedListener>>()

    companion object{
        private var SINSTANCE:MessageListRepository ?= null
        fun getInstance():MessageListRepository{

            return SINSTANCE ?: synchronized(MessageListRepository::class){
                SINSTANCE?: MessageListRepository().also {
                    SINSTANCE = it
                }
            }
        }
    }

    fun addListener(listener: MessageListChangedListener) {
        globalListeners.add(listener)
    }

    fun addListener(accountUuid: String, listener: MessageListChangedListener) {
        accountListeners.add(accountUuid to listener)
    }

    fun removeListener(listener: MessageListChangedListener) {
        globalListeners.remove(listener)

        val accountEntries = accountListeners.filter { it.second == listener }.toSet()
        if (accountEntries.isNotEmpty()) {
            accountListeners.removeAll(accountEntries)
        }
    }

    fun notifyMessageListChanged(accountUuid: String) {
        for (listener in globalListeners) {
            listener.onMessageListChanged()
        }

        for (listener in accountListeners) {
            if (listener.first == accountUuid) {
                listener.second.onMessageListChanged()
            }
        }
    }


}

fun interface MessageListChangedListener {
    fun onMessageListChanged()
}
