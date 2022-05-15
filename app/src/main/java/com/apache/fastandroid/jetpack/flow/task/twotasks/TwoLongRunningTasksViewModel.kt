package com.apache.fastandroid.jetpack.flow.task.twotasks

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

class TwoLongRunningTasksViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    fun getStatus(): LiveData<Resource<String>> {
        return status
    }

    fun startLongRunningTask() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            doLongRunningTaskOne()
                .zip(doLongRunningTaskTwo()) { resultOne, resultTwo ->
                    return@zip resultOne + resultTwo
                }
                .flowOn(Dispatchers.Default)
                .catch { e ->
                    status.postValue(Resource.error(e.toString(), null))
                }
                .collect {
                    status.postValue(Resource.success(it))
                }
        }
    }



    private fun doLongRunningTaskTwo(): Flow<String> {
        return flow {
            // your code for doing a long running task
            // Added delay to simulate
            delay(5000)
            emit("Two")
        }
    }

    private fun doLongRunningTaskOne(): Flow<String> {
        return flow {
            // your code for doing a long running task
            // Added delay to simulate
            delay(5000)
            emit("One")
        }
    }

}