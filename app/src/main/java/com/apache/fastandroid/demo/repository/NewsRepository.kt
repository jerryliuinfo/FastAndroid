package com.apache.fastandroid.demo.repository

import com.apache.fastandroid.network.model.ApiUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Created by Jerry on 2022/11/20.
 */
// NewsRepository is consumed from other layers of the hierarchy.
class NewsRepository(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val externalScope: CoroutineScope

) {


    // Mutex to make writes to cached values thread-safe.
    private val latestNewsMutex = Mutex()

    // Cache of the latest news got from the network.
    private var latestNews: List<ApiUser> = emptyList()

    suspend fun fetchLatestNews(): List<ApiUser> =
        newsRemoteDataSource.fetchLatestNews()


    /**
     * async 用于在外部作用域内启动协程。await 在新的协程上调用，以便在网络请求返回结果并且结果保存到缓存中之前，一直保持挂起状态。
     * 如果届时用户仍位于屏幕上，就会看到最新新闻；如果用户已离开屏幕，await 将被取消，但 async 内部的逻辑将继续执行。
     */
    suspend fun getLatestNews(refresh: Boolean = false): List<ApiUser> {
        if (refresh || latestNews.isEmpty()) {
           /* val networkResult = newsRemoteDataSource.fetchLatestNews()
            // Thread-safe write to latestNews
            latestNewsMutex.withLock {
                this.latestNews = networkResult
            }*/

            return externalScope.async {
                newsRemoteDataSource.fetchLatestNews().also { networkResult ->
                    // Thread-safe write to latestNews.
                    latestNewsMutex.withLock {
                        latestNews = networkResult
                    }
                }
            }.await()
        }else{
            return latestNewsMutex.withLock { this.latestNews }

        }

    }
}