package com.apache.fastandroid.jetpack.flow.api

import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.api.FlowApiService
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Jerry on 2022/5/3.
 */
class ApiHelperImpl @Inject constructor(val apiService: FlowApiService):ApiHelper {
    override fun getUsers(): Flow<List<ApiUser>> = flow {
        val data = apiService.getUsers()
        emit(data)
    }

    override suspend fun getUsers2(): List<ApiUser> {
        return apiService.getUsers2()
    }

    override  fun getUsersSingle(): Single<List<ApiUser>> {
        return apiService.getUsersSingle()
    }

    override fun getMoreUsers(): Flow<List<ApiUser>> = flow { emit(apiService.getMoreUsers()) }

    override fun getUsersWithError(): Flow<List<ApiUser>> = flow { emit(apiService.getUsersWithError()) }


}