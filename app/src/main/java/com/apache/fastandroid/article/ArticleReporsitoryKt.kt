package com.apache.fastandroid.article

import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.apache.fastandroid.network.response.BaseResponse
import com.apache.fastandroid.network.response.EmptyResponse
import com.apache.fastandroid.network.retrofit.ApiEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Jerry on 2022/2/23.
 */
class ArticleReporsitoryKt(private val network:ArticleNetwork) {

    suspend fun collect(id:Int): Result<EmptyResponse> = withContext(Dispatchers.IO){
        return@withContext network.collect(id)
    }

    //Retrofit 接口本身用 susepend 修饰符修饰能自动保证主线程安全，因此不用手动切线程
    suspend fun collect2(id:Int): Result<EmptyResponse> = network.collect2(id)

    suspend fun uncollect(id:Int):Result<EmptyResponse> = withContext(Dispatchers.IO){
        return@withContext network.uncollect(id)
    }


    companion object{
        private var instance:ArticleReporsitoryKt ?= null
        fun getInstance(network: ArticleNetwork):ArticleReporsitoryKt{
            if (instance == null){
                synchronized(ArticleReporsitoryKt::class.java){
                    instance = ArticleReporsitoryKt(network)
                }
            }
            return instance!!

        }
    }



}