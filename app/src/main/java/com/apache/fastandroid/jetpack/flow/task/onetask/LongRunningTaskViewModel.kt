package com.apache.fastandroid.jetpack.flow.task.onetask

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

class LongRunningTaskViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    fun startLongRunningTask() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            // do a long running task
            doLongRunningTask()
                .flowOn(Dispatchers.Default)
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
            // Added delay to simulate
            delay(5000)
            emit(0)
        }
    }
}