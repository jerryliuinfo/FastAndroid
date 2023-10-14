package com.apache.fastandroid.demo.app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.bean.WebsocketEvent
import com.apache.fastandroid.demo.app.base.BaseMailViewModel
import com.apache.fastandroid.demo.app.base.initiateRequest
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.network.model.DataHolder
import com.apache.fastandroid.network.model.ErrorHolder
import com.apache.fastandroid.network.model.result.BaseResponse
import com.tesla.framework.kt.asLiveData
import com.tesla.framework.kt.io
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/4/18.
 */
class MailViewModel() : BaseMailViewModel<MailRepository>() {


    private val _topArticleLiveData = MutableLiveData<List<ArticleApi>>()
    val topArticleLiveData:LiveData<List<ArticleApi>> = _topArticleLiveData

    fun loadData(){
        initiateRequest({
            _topArticleLiveData.value = mRepository.loadTopArticleCo()
        },loadState)
    }

    private val mNetwork: HomeNetwork = HomeNetwork()


    private val _responseLiveData = MutableLiveData<DataHolder<BaseResponse<List<ArticleApi>>?>>()
    val responseLiveData: LiveData<DataHolder<BaseResponse<List<ArticleApi>>?>> get() = _responseLiveData

    private var channel = Channel<WebsocketEvent?>()
    private val _connectionStatusLiveData = MutableLiveData<DataHolder<Unit?>>()
    val connectionStatusLiveData: LiveData<DataHolder<Unit?>> get() = _connectionStatusLiveData.asLiveData()

    init {
        viewModelScope.io {
            for (event in channel) {
                when (event) {
                    is WebsocketEvent.OnOpen -> {
                        _connectionStatusLiveData.postValue(
                            DataHolder.Success(Unit)
                        )
                    }
                    is WebsocketEvent.OnClosed -> {
                        _connectionStatusLiveData.postValue(
                            DataHolder.Error(ErrorHolder.SocketClosedError)
                        )
                    }
                    is WebsocketEvent.OnMessage -> {

                    }
                    is WebsocketEvent.OnFailure -> {
                        _connectionStatusLiveData.postValue(
                            DataHolder.Error(event.error)
                        )
                    }
                }
            }
        }
    }


    fun loadData2(){
        viewModelScope.launch {
            _responseLiveData.postValue(DataHolder.Loading)

            mNetwork.loadTopArticleCo()

            _responseLiveData.postValue(DataHolder.Success(mNetwork.loadTopArticleCo()))
        }
    }

}