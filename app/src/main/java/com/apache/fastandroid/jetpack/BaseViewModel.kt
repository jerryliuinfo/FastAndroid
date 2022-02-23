package com.apache.fastandroid.jetpack

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2021/5/25.
 * 继承这个BaseViewModel一定要用public修饰，否则会报 can't create class viewmodel 的错误
 */
open class BaseViewModel : ViewModel() {
    val loading = MutableLiveData(false)


    fun showLoading() {
        loading.postValue(true)
    }

    fun dismissLoading() {
        loading.postValue(false)
    }


    fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }
}