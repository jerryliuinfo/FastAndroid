package com.apache.fastandroid.home.network

import com.apache.fastandroid.base.BaseRepository
import com.apache.fastandroid.network.retrofit.ApiServiceFactory

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeNetwork {

    /**
     * ApiServiceKt 接口返回的是 Call 类型时，需要用await
     */
    suspend fun loadTopArticleCo() = ApiServiceFactory.apiService.loadTopArticleCo()

    suspend fun loadHomeArticleCo(pageNum:Int) = ApiServiceFactory.apiService.loadHomeArticleCo(pageNum)



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