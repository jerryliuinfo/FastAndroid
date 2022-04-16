package com.apache.fastandroid.network.retrofit

import com.apache.fastandroid.network.model.Article
import com.apache.fastandroid.network.model.HomeArticleResponse
import com.apache.fastandroid.network.model.Repo
import com.apache.fastandroid.network.model.ResultData
import com.apache.fastandroid.network.response.BaseResponse
import com.apache.fastandroid.network.response.EmptyResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


/**
 * Description:TODO
 * Create Time:2021/12/22 9:47 下午
 * Author:jerry
 *
 */

interface ApiServiceKt {

    @GET("/article/top/json")
// suspend fun loadTopArticleCo(): Call<BaseResponse<List<Article>?>>
    suspend fun loadTopArticleCo(): BaseResponse<List<Article>>?

    @GET("/article/list/{pageNum}/json")
    suspend fun loadHomeArticleCo(@Path("pageNum") pageNum: Int): BaseResponse<HomeArticleResponse>?


    //返回 call 时不要使用 suspend 操作符，否则会报错, 参考:https://stackoverflow.com/questions/58429501/unable-to-invoke-no-args-constructor-for-retrofit2-call
    @POST("/lg/collect/{id}/json")
    fun collect(@Path("id") id: Int): Call<BaseResponse<EmptyResponse>>

    @POST("/lg/uncollect_originId/{id}/json")
    fun unCollect(@Path("id") id: Int): Call<BaseResponse<EmptyResponse>>

    /**
     * Retrofit 识别到 suspend 关键字会自动保证主线程安全
     */
    @POST("/lg/collect/{id}/json")
    suspend fun collect2(@Path("id") id: Int): BaseResponse<EmptyResponse>


    //Retrofit 识别到 suspend 关键字会自动保证主线程安全
    @GET("/user/{user}/repos")
    suspend fun listReposKt(@Path("user") user: String): List<Repo>

    @GET("/user/{user}/repos")
    suspend fun listReposKtWithErrorHandle(@Path("user") user: String): Response<List<Repo>>

    @GET("/user/{user}/repos")
    fun listReposRx(@Path("user") user: String): Single<List<Repo>>

    @GET("article/get/{id}")
    suspend fun getArticleById(@Path("id") id: Long): ResultData<Repo>
}