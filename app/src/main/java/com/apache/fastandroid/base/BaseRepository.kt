package com.apache.fastandroid.base

import com.apache.fastandroid.network.retrofit.ApiService
import com.apache.fastandroid.network.retrofit.RetrofitFactory
import com.apache.fastandroid.retrofit.ApiConstant.DISNEY_URL

/**
 * Created by Jerry on 2022/4/16.
 */
open class BaseRepository {
     val apiService:ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }

    protected val disneyService:ApiService =  RetrofitFactory.instance.create(ApiService::class.java,DISNEY_URL)
}