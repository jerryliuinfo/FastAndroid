package com.apache.fastandroid.demo.app.base

import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonParseException
import com.tesla.framework.component.viewstate.State
import com.zwb.mvvm_mall.base.viewstate.StateType
import org.apache.http.conn.ConnectTimeoutException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Created with Android Studio.
 * Description:
 * @CreateDate: 2020/5/5 11:32
 */

const val COMMON_KEY = "url_common_key"

object NetExceptionHandle {
    fun handleException(e: Throwable?, loadState: MutableLiveData<State>){
        e?.let {
            when (it) {
                is HttpException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,COMMON_KEY))
                }
                is ConnectException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,COMMON_KEY))
                }
                is ConnectTimeoutException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,COMMON_KEY))
                }
                is UnknownHostException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,COMMON_KEY))
                }
                is JsonParseException -> {
                    loadState.postValue(State(StateType.NETWORK_ERROR,COMMON_KEY))
                }
            }
        }
    }
}