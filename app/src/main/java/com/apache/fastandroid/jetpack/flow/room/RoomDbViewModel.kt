package com.apache.fastandroid.jetpack.flow.room

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apache.fastandroid.jetpack.BaseViewModel
import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.jetpack.flow.local.DatabaseHelper
import com.apache.fastandroid.network.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Created by Jerry on 2022/5/4.
 */
class RoomDbViewModel(private val apiHelper: ApiHelper,private val databaseHelper: DatabaseHelper):BaseViewModel() {

    val users = MutableLiveData<Resource<List<User>>>()

    init {
        fetchUsers()
    }

    fun fetchUsers(){
        viewModelScope.launch {
            users.postValue(Resource.loading(null))
            databaseHelper.getUsers()
                .flatMapConcat { usersFromDb ->
                    if (usersFromDb.isNullOrEmpty()) {
                        return@flatMapConcat apiHelper.getUsers().map { apiUserList ->
                            val userList = mutableListOf<User>()
                            for (apiUser in apiUserList) {
                                userList.add(
                                    User(
                                        apiUser.id,
                                        apiUser.name,
                                        apiUser.email,
                                        apiUser.avatar
                                    )
                                )
                            }
                            userList
                        }.flatMapConcat { usersInsertToDb ->
                            databaseHelper.insertAll(usersInsertToDb)
                                .flatMapConcat {
                                    flow {
                                        emit(usersInsertToDb)
                                    }
                                }
                        }
                    } else {
                        return@flatMapConcat flow {
                            emit(usersFromDb)
                        }
                    }
                }.flowOn(Dispatchers.IO)
                .catch { e->
                    users.postValue(Resource.error(e.toString(),null))
                }.collect{
                    users.postValue(Resource.success(it))
                }

        }
    }
}