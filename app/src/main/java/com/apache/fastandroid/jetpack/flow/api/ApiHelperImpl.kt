package com.apache.fastandroid.jetpack.flow.api

import com.apache.fastandroid.jetpack.flow.data.bean.User
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.retrofit.FlowApiService
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Created by Jerry on 2022/5/3.
 */
class ApiHelperImpl(val apiService: FlowApiService):ApiHelper {
    override fun getUsers(): Flow<List<ApiUser>> = flow { emit(apiService.getUsers()) }

    override suspend fun getUsers2(): List<ApiUser> {
        return apiService.getUsers2()
    }

    override  fun getUsersSingle(): Single<List<ApiUser>> {
        return apiService.getUsersSingle()
    }

    override fun getMoreUsers(): Flow<List<ApiUser>> = flow { emit(apiService.getMoreUsers()) }

    override fun getUsersWithError(): Flow<List<ApiUser>> = flow { emit(apiService.getUsersWithError()) }


}