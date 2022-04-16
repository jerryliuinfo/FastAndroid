package com.apache.fastandroid.base

import com.apache.fastandroid.network.retrofit.ApiService
import com.apache.fastandroid.network.retrofit.RetrofitFactory

/**
 * Created by Jerry on 2022/4/16.
 */
open class BaseRepository {
    protected val apiService:ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }
}