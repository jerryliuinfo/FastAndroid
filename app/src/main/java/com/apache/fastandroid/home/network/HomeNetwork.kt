package com.apache.fastandroid.home.network

import com.apache.fastandroid.network.response.EmptyResponse
import com.apache.fastandroid.network.retrofit.ApiEngine
import retrofit2.await

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeNetwork {
    private val apiServiceKt = ApiEngine.getApiServiceKt()

    suspend fun loadTopArticleCo() = apiServiceKt.loadTopArticleCo()

    suspend fun loadHomeArticleCo(pageNum:Int) = apiServiceKt.loadHomeArticleCo(pageNum)

    suspend fun collect(id:Int)  = apiServiceKt.collect(id).await()

    suspend fun uncollect(id:Int)  = apiServiceKt.unCollect(id).await()

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