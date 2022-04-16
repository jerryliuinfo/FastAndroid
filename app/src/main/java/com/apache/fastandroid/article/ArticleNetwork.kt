package com.apache.fastandroid.article

import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.apache.fastandroid.network.retrofit.BaseNetwork
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
class ArticleNetwork:BaseNetwork() {
    private val apiServiceKt = ApiEngine.apiServiceKt

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