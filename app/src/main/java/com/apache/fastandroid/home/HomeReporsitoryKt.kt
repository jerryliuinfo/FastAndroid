package com.apache.fastandroid.home

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
class HomeReporsitoryKt(private val network: HomeNetwork) {

    suspend fun loadHomeArticleCo(pageNum:Int): HomeArticleResponse = withContext(Dispatchers.IO){
        var artices = network.loadHomeArticleCo(pageNum)
        artices!!.data
    }

    suspend fun loadTopArticleCo():List<Article>? = withContext(Dispatchers.IO){
        var artices = network.loadTopArticleCo()
        artices!!.data
    }

    suspend fun collect(id:Int):EmptyResponse = withContext(Dispatchers.IO){
        network.collect(id).data
    }

    suspend fun uncollect(id:Int):EmptyResponse = withContext(Dispatchers.IO){
        network.uncollect(id).data
    }


    companion object{
        private var instance:HomeReporsitoryKt ?= null
        fun getInstance(network: HomeNetwork):HomeReporsitoryKt{
            if (instance == null){
                synchronized(HomeReporsitoryKt::class.java){
                    instance = HomeReporsitoryKt(network)
                }
            }
            return instance!!

        }
    }



}