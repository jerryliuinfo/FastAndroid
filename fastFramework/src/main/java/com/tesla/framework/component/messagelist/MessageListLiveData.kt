package com.tesla.framework.component.messagelist

import androidx.lifecycle.LiveData

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.prefs.Preferences

class MessageListLiveData(
    private val messageListLoader: MessageListLoader,
    private val messageListRepository: MessageListRepository,
    private val coroutineScope: CoroutineScope,
) : LiveData<MessageListInfo>() {

    private val messageListChangedListener = MessageListChangedListener {
        loadMessageListAsync()
    }

    private fun loadMessageListAsync() {
        coroutineScope.launch(Dispatchers.Main) {
            val messageList = withContext(Dispatchers.IO) {
                messageListLoader.getMessageList()
            }
            value = messageList
        }
    }

    override fun onActive() {
        super.onActive()

        registerMessageListChangedListenerAsync()
        loadMessageListAsync()
    }

    override fun onInactive() {
        super.onInactive()
        messageListRepository.removeListener(messageListChangedListener)
    }

    private fun registerMessageListChangedListenerAsync() {
        coroutineScope.launch(Dispatchers.IO) {
            val accountUuids = arrayOf<String>()

            for (accountUuid in accountUuids) {
                messageListRepository.addListener(accountUuid, messageListChangedListener)
            }
        }
    }
}

data class MessageListItem(
    val subject: String?,
    val threadCount: Int,
    val messageDate: Long,
    val internalDate: Long,
    val displayName: CharSequence,
    val previewText: String,
    val isMessageEncrypted: Boolean,
    val isRead: Boolean,
    val isStarred: Boolean,
    val isAnswered: Boolean,
    val isForwarded: Boolean,
    val hasAttachments: Boolean,
    val uniqueId: Long,
    val folderId: Long,
    val messageUid: String,
    val databaseId: Long,
    val threadRoot: Long,
) {

}

data class MessageListInfo(val messageListItems: List<MessageListItem>, val hasMoreMessages: Boolean)
