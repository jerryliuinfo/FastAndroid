package com.apache.fastandroid.network.retrofit

import com.apache.fastandroid.retrofit.ApiConstant

/**
 * Created by Jerry on 2022/5/3.
 */
object ApiServiceFactory {
    val apiService:ApiService by lazy {
        RetrofitFactory.instance.create(ApiService::class.java)
    }

    val disneyService:ApiService =  RetrofitFactory.instance.create(ApiService::class.java,
        ApiConstant.DISNEY_URL
    )

    val flowService:FlowApiService =  RetrofitFactory.instance.create(FlowApiService::class.java,
        ApiConstant.FLOW_BASE_URL
    )
}