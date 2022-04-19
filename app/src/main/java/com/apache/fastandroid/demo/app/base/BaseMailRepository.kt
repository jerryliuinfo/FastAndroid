package com.apache.fastandroid.demo.app.base

import com.apache.fastandroid.network.retrofit.ApiService
import com.apache.fastandroid.network.retrofit.RetrofitFactory

/**
 * Created by Jerry on 2022/4/18.
 */
open class BaseMailRepository {
    protected val apiService: ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }
}