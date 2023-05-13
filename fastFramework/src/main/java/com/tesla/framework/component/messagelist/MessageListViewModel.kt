package com.tesla.framework.component.messagelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope


class MessageListViewModel(private val messageListLiveDataFactory: MessageListLiveDataFactory) : ViewModel() {
    private var currentMessageListLiveData: MessageListLiveData? = null
    private val messageListLiveData = MediatorLiveData<MessageListInfo>()


    fun getMessageListLiveData(): LiveData<MessageListInfo> {
        return messageListLiveData
    }

    fun loadMessageList() {

        removeCurrentMessageListLiveData()

        val liveData = messageListLiveDataFactory.create(viewModelScope)
        currentMessageListLiveData = liveData

        messageListLiveData.addSource(liveData) { items ->
            messageListLiveData.value = items
        }
    }

    private fun removeCurrentMessageListLiveData() {
        currentMessageListLiveData?.let {
            currentMessageListLiveData = null
            messageListLiveData.removeSource(it)
        }
    }
}
