package com.apache.fastandroid.network.retrofit

import com.apache.fastandroid.network.model.Repo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * Description:TODO
 * Create Time:2021/12/22 9:47 下午
 * Author:jerry
 *
 */

interface ApiServiceKt {

 @GET("/user/{user}/repos")
  fun listRepos(@Path("user") user:String): Call<List<Repo>>

  @GET("/user/{user}/repos")
  suspend fun listReposKt(@Path("user") user:String):List<Repo>


 @GET("/user/{user}/repos")
  fun listReposRx(@Path("user") user:String): Single<List<Repo>>


 @GET("/user/{user}/repos")
 fun listReposRx2(@Path("user") user:String): Observable<List<Repo>>
}