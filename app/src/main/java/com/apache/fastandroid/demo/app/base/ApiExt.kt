package com.apache.fastandroid.demo.app.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.network.response.BaseResponse
import com.zwb.mvvm_mall.base.viewstate.State
import com.zwb.mvvm_mall.base.viewstate.StateType
import kotlinx.coroutines.launch

/**
 * Created with Android Studio.
 * Description:数据解析扩展函数
 * @CreateDate: 2020/4/19 17:35
 */

fun <T> BaseResponse<T>.dataConvert(
    loadState: MutableLiveData<State>,
    urlKey:String = ""
): T {
    return when (errorCode) {
        0 -> {
            if (data is List<*>) {
                if ((data as List<*>).isEmpty()) {
                    loadState.postValue(State(StateType.EMPTY,urlKey))
                }
            }else{
                loadState.postValue(State(StateType.SUCCESS,urlKey))
            }
            data
        }
        else -> {
            loadState.postValue(State(StateType.ERROR,urlKey, message = errorMsg))
            data
        }
    }
}


fun <T : BaseMailRepository> BaseMailViewModel<T>.initiateRequest(
    block: suspend () -> Unit,
    loadState: MutableLiveData<State>
) {
    viewModelScope.launch {
        runCatching {
            block()
        }.onSuccess {
        }.onFailure {
            NetExceptionHandle.handleException(it, loadState)
        }
    }
}
