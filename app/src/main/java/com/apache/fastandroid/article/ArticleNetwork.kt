package com.apache.fastandroid.article

import com.apache.fastandroid.network.retrofit.BaseNetwork
import com.apache.fastandroid.network.retrofit.RetrofitFactory
import retrofit2.await

/**
 * Created by Jerry on 2022/2/23.
 */
class ArticleNetwork:BaseNetwork() {
    private val apiServiceKt = RetrofitFactory.instance.apiService

    /**
     * 以 Call 形式返回结果
     */
    suspend fun collect(id:Int)  = getResult {
        apiServiceKt.collect(id).await()
    }

    /**
     * 使用 suspend 关键字直接返回结果
     */
    suspend fun collect2(id:Int)  = getResult {
        apiServiceKt.collect2(id)
    }

    suspend fun uncollect(id:Int)  = getResult {
        apiServiceKt.unCollect(id).await()
    }


    companion object{
        @Volatile
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