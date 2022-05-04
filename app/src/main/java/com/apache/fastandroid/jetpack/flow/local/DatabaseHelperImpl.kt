package com.apache.fastandroid.jetpack.flow.local

import com.apache.fastandroid.jetpack.flow.data.bean.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override fun getUsers(): Flow<List<User>> =
        flow { emit(appDatabase.userDao().getAll()) }

    override fun insertAll(users: List<User>): Flow<Unit> = flow {
        appDatabase.userDao().insertAll(users)
        emit(Unit)
    }

}