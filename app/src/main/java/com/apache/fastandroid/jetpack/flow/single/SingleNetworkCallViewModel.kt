package com.apache.fastandroid.jetpack.flow.single

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/5/3.
 */
class SingleNetworkCallViewModel(private val apiHelper: ApiHelper,private val dbHelper: DatabaseHelper?=null):BaseViewModel() {

    val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUser()
    }

    fun fetchUser(){
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            apiHelper.getUsers()
                .catch { e->
                    users.postValue(Resource.error(e.toString(), null))
                }.collect{
                    users.postValue(Resource.success(it))
                }

        }
    }
}