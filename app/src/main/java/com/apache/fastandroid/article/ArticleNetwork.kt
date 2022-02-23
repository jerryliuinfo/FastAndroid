package com.apache.fastandroid.article

import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.retrofit.ApiEngine
import retrofit2.await

/**
 * Created by Jerry on 2022/2/23.
 */
class ArticleNetwork {
    private val apiServiceKt = ApiEngine.getApiServiceKt()

    suspend fun collect(id:Int)  = apiServiceKt.collect(id).await()

    suspend fun uncollect(id:Int)  = apiServiceKt.unCollect(id).await()

    companion object{
        private var instance: ArticleNetwork?= null
        fun getInstance(): ArticleNetwork {
            if (instance == null){
                synchronized(ArticleNetwork::class.java){
                    if (instance == null){
                        instance = ArticleNetwork()
                    }
                }
            }
            return instance!!
        }
    }
}