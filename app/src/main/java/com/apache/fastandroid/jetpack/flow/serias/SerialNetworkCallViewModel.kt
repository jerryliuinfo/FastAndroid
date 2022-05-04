package com.apache.fastandroid.jetpack.flow.serias

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

/**
 * Created by Jerry on 2022/5/3.
 */
class SerialNetworkCallViewModel(private val apiHelper: ApiHelper, private val dbHelper: DatabaseHelper?=null):BaseViewModel() {

    val users = MutableLiveData<Resource<List<ApiUser>>>()

    init {
        fetchUser()
    }

    fun fetchUser(){

            viewModelScope.launch {
                val costTime = measureTimeMillis {
                    users.postValue(Resource.loading(null))
                    val allUsersFromApi = mutableListOf<ApiUser>()
                    apiHelper.getUsers().flatMapConcat {
                        allUsersFromApi.addAll(it)
                        apiHelper.getMoreUsers()
                    }.flowOn(Dispatchers.IO)
                        .catch { e->
                            users.postValue(Resource.error(e.toString(), null))
                        }.collect{
                            allUsersFromApi.addAll(it)
                            users.postValue(Resource.success(allUsersFromApi))
                        }

                }
                println("costTime: $costTime")
        }


    }
}