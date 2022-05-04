package com.apache.fastandroid.jetpack.flow.local

import com.apache.fastandroid.jetpack.flow.data.bean.User
import kotlinx.coroutines.flow.Flow

interface DatabaseHelper {

    fun getUsers(): Flow<List<User>>

    fun insertAll(users: List<User>):Flow<Unit>

}