package com.apache.fastandroid.jetpack.flow.api

import com.apache.fastandroid.network.model.ApiUser
import kotlinx.coroutines.flow.Flow

interface ApiHelper {

    fun getUsers(): Flow<List<ApiUser>>

    fun getMoreUsers(): Flow<List<ApiUser>>

    fun getUsersWithError(): Flow<List<ApiUser>>

}