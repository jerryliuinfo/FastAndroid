package com.apache.fastandroid.network.api

import com.apache.fastandroid.network.retrofit.ApiConstant
import com.apache.fastandroid.network.retrofit.RetrofitFactory


/**
 * Created by Jerry on 2022/5/3.
 */
object ApiServiceFactory {
    val apiService: ApiService by lazy {
        RetrofitFactory.get().create(ApiService::class.java)
    }

    val disneyService: ApiService =  RetrofitFactory.get().create(
        ApiService::class.java,
        ApiConstant.DISNEY_URL
    )

    val flowService: FlowApiService =  RetrofitFactory.get().create(
        FlowApiService::class.java,
        ApiConstant.FLOW_BASE_URL
    )



    fun <T> createApi(clazz: Class<T>):T {
        return RetrofitFactory.get().create(clazz)
    }

}