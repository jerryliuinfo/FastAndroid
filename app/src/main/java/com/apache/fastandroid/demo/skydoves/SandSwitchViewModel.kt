package com.apache.fastandroid.demo.skydoves

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.network.model.Poster
import com.skydoves.sandwich.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by Jerry on 2022/5/1.
 */
class SandSwitchViewModel:BaseViewModel() {

    private val repository = SandWitchRepository.getInstance()

    private val _posterListFlow = MutableStateFlow<List<Poster>?>(emptyList())

    val posterListFlow: StateFlow<List<Poster>?> = _posterListFlow

    private val _posterListLiveData = MutableLiveData<List<Poster>?>(emptyList())
    val posterListLiveData:LiveData<List<Poster>?> = _posterListLiveData


    val toastLiveData = MutableLiveData<String>()

    fun loadDataByCall(){
        Timber.d("loadDataByCall -->")
        repository.fetchPostersByCall().request { response ->
            when(response){
                is ApiResponse.Success -> {
                    println("loadDataByCall onSuccess --->")
                    _posterListLiveData.postValue(response.data)
                }
                is ApiResponse.Failure.Error ->{
                    println("loadDataByCall Failure.Error --->")

                    // handles error cases depending on the status code.
                    when (response.statusCode) {
                        StatusCode.InternalServerError -> toastLiveData.postValue("InternalServerError")
                        StatusCode.BadGateway -> toastLiveData.postValue("BadGateway")
                        else -> toastLiveData.postValue("$response.statusCode(${response.statusCode.code}): ${response.message()}")
                    }
                }
                is ApiResponse.Failure.Exception ->{
                    println("loadDataByCall Failure.Exception --->")
                    toastLiveData.postValue(response.message!!)

                }
            }

        }
    }

    fun loadByCoroutine(){
        Timber.d("initialized MainViewModel.")

        viewModelScope.launch {
            repository.fetchPostersByCoroutine()
                // handles the success scenario when the API request succeeds.
                .suspendOnSuccess {
                    _posterListFlow.emit(data)
                    println("onSuccess: ${this}}")

                    _posterListLiveData.postValue(data)
                }
                // handles the error scenario when the API request fail.
                // e.g., internal server error.
                .onError {
                    // handles error cases depending on the status code.
                    val message = when (statusCode) {
                        StatusCode.InternalServerError -> "InternalServerError"
                        StatusCode.BadGateway -> "BadGateway"
                        else -> "$statusCode(${statusCode.code}): ${message()}"
                    }
                    toastLiveData.postValue(message)
                }
                // handles the error scenario when an unexpected exception happens.
                // e.g., network connection error, timeout.
                .onException {
                    toastLiveData.postValue(message!!)
                }
        }
    }

    fun loadData1(){
        Timber.d("initialized MainViewModel.")
        viewModelScope.launch {
        }
    }




}