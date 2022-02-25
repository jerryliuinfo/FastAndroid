package com.apache.fastandroid.home

import com.apache.fastandroid.home.db.HomeDao
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.apache.fastandroid.network.response.EmptyResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlin.random.Random

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeReporsitoryKt(private val homeDao:HomeDao, private val network: HomeNetwork,private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO, ) {

    suspend fun loadHomeArticleCo(pageNum:Int): HomeArticleResponse = withContext(defaultDispatcher){

        delay(10000)
        var artices = network.loadHomeArticleCo(pageNum)
        if (artices != null){
            homeDao.cacheHomeData(pageNum,artices.data)
            return@withContext artices.data
        }else{
            return@withContext homeDao.getCacheHomeData(pageNum)
        }
    }

    suspend fun loadTopArticleCo():List<Article>? = withContext(Dispatchers.IO){
        delay(Random.nextLong(500,1000))
        println("loadTopArticleCo thread: ${Thread.currentThread().name}")
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
        fun getInstance(dao: HomeDao,network: HomeNetwork):HomeReporsitoryKt{
            if (instance == null){
                synchronized(HomeReporsitoryKt::class.java){
                    instance = HomeReporsitoryKt(dao,network)
                }
            }
            return instance!!

        }
    }

    suspend fun testData1():String = withContext(Dispatchers.IO){
        delay(Random.nextInt(500,3000).toLong())
        "testData1"
    }
    suspend fun testData2():String = withContext(Dispatchers.IO){
        delay(Random.nextInt(1000,4000).toLong())
        "testData2"
    }


}