package com.apache.fastandroid.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Jerry on 2023/6/14.
 */
class RefreshTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken =  ""//our access Token
        val request = chain.request().newBuilder()
            .addHeader("Authorization", accessToken)
            .build()
        val response = chain.proceed(request)
        if (response.code == 401) {
            val newToken: String? =   fetchToken()//fetch from some other source
                if (newToken != null) {
                    val newRequest =  chain.request().newBuilder()
                        .addHeader("Authorization", newToken)
                        .build()
                    return chain.proceed(newRequest)
                }
        }
        return response
    }

    private fun fetchToken():String{
        return ""
    }
}