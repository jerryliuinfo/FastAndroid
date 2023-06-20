package com.apache.fastandroid.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Jerry on 2021/3/23.
 */
class ErrorInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val response = chain.proceed(builder.build())
        when (response.code) {
            400 -> {
                //Show Bad Request Error Message
            }
            401 -> {
                //Show UnauthorizedError Message
            }
            403 -> {
                //Show Forbidden Message
            }
            404 -> {
                //Show NotFound Message
            }
            // ... and so on
        }

        return response
    }

}