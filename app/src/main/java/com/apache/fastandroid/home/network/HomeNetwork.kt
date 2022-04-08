package com.apache.fastandroid.home.network

import com.apache.fastandroid.network.response.EmptyResponse
import com.apache.fastandroid.network.retrofit.ApiEngine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeNetwork {
    private val apiServiceKt = ApiEngine.apiServiceKt

    /**
     * ApiServiceKt 接口返回的是 Call 类型时，需要用await
     */
    suspend fun loadTopArticleCo() = apiServiceKt.loadTopArticleCo()

    suspend fun loadHomeArticleCo(pageNum:Int) = apiServiceKt.loadHomeArticleCo(pageNum)







    companion object{
        private var instance:HomeNetwork ?= null
        fun getInstance():HomeNetwork{
            if (instance == null){
                synchronized(HomeNetwork::class.java){
                    if (instance == null){
                        instance = HomeNetwork()
                    }
                }
            }
            return instance!!
        }
    }
}