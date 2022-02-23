package com.apache.fastandroid.article

import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.apache.fastandroid.network.response.EmptyResponse
import com.apache.fastandroid.network.retrofit.ApiEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Created by Jerry on 2022/2/23.
 */
class ArticleReporsitoryKt(private val network:ArticleNetwork) {

    suspend fun collect(id:Int):EmptyResponse = withContext(Dispatchers.IO){
        network.collect(id).data
    }

    suspend fun uncollect(id:Int):EmptyResponse = withContext(Dispatchers.IO){
        network.uncollect(id).data
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