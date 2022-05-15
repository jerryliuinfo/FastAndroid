package com.apache.fastandroid.jetpack.flow.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.network.model.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MapViewModel(
    val apiHelper: ApiHelper,
    dbHelper: DatabaseHelper
) : ViewModel() {

    private val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            apiHelper.getUsers()
                .map { apiUserList ->
                    val userList = mutableListOf<User>()
                    for (apiUser in apiUserList) {
                        val user = User(
                            apiUser.id,
                            apiUser.name,
                            apiUser.email,
                            apiUser.avatar
                        )
                        userList.add(user)
                    }
                    userList
                }
                .catch { e ->
                    users.postValue(Resource.error(e.toString(), null))
                }
                .collect {
                    users.postValue(Resource.success(it))
                }
        }
    }

    fun getUsers(): LiveData<Resource<List<User>>> {
        return users
    }
}