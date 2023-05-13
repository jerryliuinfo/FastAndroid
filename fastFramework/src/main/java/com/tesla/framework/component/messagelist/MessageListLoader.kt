package com.tesla.framework.component.messagelist

import timber.log.Timber

class MessageListLoader(
) {

    fun getMessageList(): MessageListInfo {
        return try {
            getMessageListInfo()
        } catch (e: Exception) {
            Timber.e(e, "Error while fetching message list")

            // TODO: Return an error object instead of an empty list
            MessageListInfo(messageListItems = emptyList(), hasMoreMessages = false)
        }
    }

    private fun getMessageListInfo(): MessageListInfo {

        return MessageListInfo(listOf(), hasMoreMessages = true)
    }


}


