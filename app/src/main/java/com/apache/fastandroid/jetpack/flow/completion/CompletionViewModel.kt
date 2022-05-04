package com.apache.fastandroid.jetpack.flow.completion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.network.model.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class CompletionViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val status = MutableLiveData<Resource<String>>()

    init{
        fetchUsers()
    }
    private fun fetchUsers() {
        viewModelScope.launch {
            status.postValue(Resource.loading(null))
            apiHelper.getUsers()
                .catch { e ->
                    status.postValue(Resource.error(e.toString(), null))
                }
                .onCompletion {
                    status.postValue(Resource.success("Task Completed"))
                }
                .collect {
                }
        }
    }

    fun getStatus(): LiveData<Resource<String>> {
        return status
    }
    
}