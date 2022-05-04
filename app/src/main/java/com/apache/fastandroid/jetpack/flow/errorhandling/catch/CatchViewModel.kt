package com.apache.fastandroid.jetpack.flow.errorhandling.catch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CatchViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<ApiUser>>>()

    fun getUsers(): LiveData<Resource<List<ApiUser>>> {
        return users
    }

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            apiHelper.getUsersWithError()
                .catch { e ->
                    users.postValue(Resource.error(e.toString(), null))
                }
                .collect {
                    users.postValue(Resource.success(it))
                }
        }
    }



}