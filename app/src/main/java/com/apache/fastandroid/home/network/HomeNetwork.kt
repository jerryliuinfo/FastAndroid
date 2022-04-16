package com.apache.fastandroid.home.network

import com.apache.fastandroid.base.BaseRepository

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeNetwork :BaseRepository(){

    /**
     * ApiServiceKt 接口返回的是 Call 类型时，需要用await
     */
    suspend fun loadTopArticleCo() = apiService.loadTopArticleCo()

    suspend fun loadHomeArticleCo(pageNum:Int) = apiService.loadHomeArticleCo(pageNum)



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