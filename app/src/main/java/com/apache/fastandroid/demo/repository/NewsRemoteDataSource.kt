package com.apache.fastandroid.demo.repository

import com.apache.fastandroid.jetpack.flow.api.ApiHelper
import com.apache.fastandroid.network.model.ApiUser
import com.apache.fastandroid.network.model.Repo
import com.apache.fastandroid.network.retrofit.ApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

/**
 * Created by Jerry on 2022/11/20.
 */
class NewsRemoteDataSource(
    private val newsApi: ApiHelper,
    private val ioDispatcher: CoroutineDispatcher
) {
    /**
     * Fetches the latest news from the network and returns the result.
     * This executes on an IO-optimized thread pool, the function is main-safe.
     */
    suspend fun fetchLatestNews(): List<ApiUser> =
    // Move the execution to an IO-optimized thread since the ApiService
        // doesn't support coroutines and makes synchronous requests.
        withContext(ioDispatcher) {
            newsApi.getUsers2()
        }
}
