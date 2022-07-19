package com.apache.fastandroid.jetpack.flow.retry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.network.model.Resource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

class RetryViewModel(
    val apiHelper: ApiHelper,
    dbHelper: DatabaseHelper
) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    init {
        startTask()
    }

    fun startTask() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            // do a long running task
            doLongRunningTask()
                .flowOn(Dispatchers.Default)
                .retry(retries = 1) { cause ->
                    if (cause is IOException) {
                        delay(2000)
                        return@retry true
                    } else {
                        return@retry false
                    }
                }
                .catch {
                    status.postValue(Resource.error("Something Went Wrong", null))
                }
                .collect {
                    status.postValue(Resource.success("Task Completed"))
                }
        }
    }

    fun getStatus(): LiveData<Resource<String>> {
        return status
    }

    private fun doLongRunningTask(): Flow<Int> {
        return flow {
            // your code for doing a long running task
            // Added delay, random number, and exception to simulate

            delay(2000)

            val randomNumber = (0..2).random()

            if (randomNumber == 0) {
                throw IOException()
            } else if (randomNumber == 1) {
                throw IndexOutOfBoundsException()
            }

            delay(2000)
            emit(0)
        }
    }

}