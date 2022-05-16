package com.apache.fastandroid.network.retrofit

import com.apache.fastandroid.network.model.ApiUser
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

/**
 * Created by Jerry on 2022/5/3.
 */
interface FlowApiService {
    @GET("users")
    suspend fun getUsers(): List<ApiUser>

    @GET("users")
    suspend fun getUsers2(): List<ApiUser>

    @GET("more-users")
    suspend fun getMoreUsers(): List<ApiUser>

    @GET("error")
    suspend fun getUsersWithError(): List<ApiUser>
}