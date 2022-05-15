package com.apache.fastandroid.jetpack.flow.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.network.model.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FilterViewModel(
    apiHelper: ApiHelper,
    dbHelper: DatabaseHelper
) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

  /*  private val status by lazy {
        MutableLiveData<Resource<String>>().also {
            startFilterTask()
        }
    }
*/
    fun startFilterTask() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            val result = mutableListOf<Int>()
            (1..5).asFlow()
                .filter {
                    it % 2 == 0
                }.onEach {
                    delay(1000)
                }
                .toList(result)

            status.postValue(Resource.success(result.toString()))
        }
    }

    fun getStatus(): LiveData<Resource<String>> {
        return status
    }

}