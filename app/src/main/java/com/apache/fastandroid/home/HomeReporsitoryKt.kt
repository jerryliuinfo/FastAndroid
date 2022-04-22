package com.apache.fastandroid.home

import com.apache.fastandroid.home.db.HomeDao
import com.apache.fastandroid.home.network.HomeNetwork
import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.apache.fastandroid.network.response.EmptyResponse
import com.apache.fastandroid.util.extensitons.PageState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception
import kotlin.random.Random

/**
 * Created by Jerry on 2022/2/23.
 */
class HomeReporsitoryKt(private val homeDao:HomeDao, private val network: HomeNetwork) {

    suspend fun loadTopArticleCo():List<Article>?{
        println("loadTopArticleCo thread: ${Thread.currentThread().name}")
        var artices = network.loadTopArticleCo()
        return artices?.data
    }

    suspend fun loadTopArticleCoByPageStatus():PageState<List<Article>>{
        val apiResponse =   try {
            network.loadTopArticleCo()
        }catch (e:Exception){
            e.printStackTrace()
           return PageState.Error(e)
        }

        return apiResponse.data?.let {
            PageState.Success(it)
        } ?: kotlin.run {
            PageState.Error("Failed to get News")
        }

    }



    suspend fun loadHomeArticleCo(pageNum:Int): HomeArticleResponse? {
        var artices = network.loadHomeArticleCo(pageNum)
        if (artices != null){
            homeDao.cacheHomeData(pageNum,artices.data)
            return artices.data
        }else{
            return homeDao.getCacheHomeData(pageNum)
        }
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