package com.apache.fastandroid.demo.kt.lazy

import com.apache.fastandroid.network.api.ApiService
import com.apache.fastandroid.network.api.ApiServiceFactory
import com.apache.fastandroid.network.model.ArticleApi
import com.apache.fastandroid.network.model.result.BaseResponse

/**
 * Created by Jerry on 2024/1/20.
 */
class LazyApi {

    private val apiService:ApiService = ApiServiceFactory.apiService

    suspend fun loadTopArticleCo(): BaseResponse<List<ArticleApi>>   {
        return apiService.loadTopArticleCo()
    }

    companion object{
        val instance:Lazy<LazyApi> = lazy { LazyApi() }
    }
}