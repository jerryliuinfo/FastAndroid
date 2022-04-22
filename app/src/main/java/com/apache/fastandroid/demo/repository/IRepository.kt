package com.apache.fastandroid.demo.repository

import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.response.BaseResponse

/**
 * Created by Jerry on 2022/4/22.
 */
interface IRepository {
    suspend fun loadTopArticleCo(): BaseResponse<List<Article>>

}