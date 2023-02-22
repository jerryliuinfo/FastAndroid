package com.apache.fastandroid.jetpack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.apache.fastandroid.jetpack.reporsity.MessageRepository
import com.apache.fastandroid.jetpack.reporsity.UserReporsity
import com.tesla.framework.component.loader.LoaderState
import com.tesla.framework.component.loader.liveDataLoader
import kotlin.random.Random

private typealias MessageHeaderState = LoaderState<List<String>>

class MessageHeadersViewModel : ViewModel() {
    private var messageHeaderLiveData: LiveData<MessageHeaderState>? = null

    private val repository: MessageRepository by lazy {
        MessageRepository()
    }

    fun loadHeaders(): LiveData<MessageHeaderState> {
        return messageHeaderLiveData ?: loadMessageHeader().also { messageHeaderLiveData = it }
    }

    private  fun loadMessageHeader(): LiveData<MessageHeaderState> {
        return liveDataLoader {
           repository.getList()
        }
    }
}