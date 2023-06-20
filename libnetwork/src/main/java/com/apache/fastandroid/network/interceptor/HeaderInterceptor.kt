package com.apache.fastandroid.network.interceptor

import android.os.Build

import okhttp3.*
import java.io.IOException



/**
 * Created by Jerry on 2021/3/23.
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest: Request = chain.request()
        //添加公共请求头
        val newBuilder = originRequest.newBuilder()
            .apply {
                addHeader("brand", Build.BRAND)
                addHeader("model", Build.MODEL)
                addHeader("Content-Type", "application/json")
                addHeader("charset", "UTF-8")
            }

        return chain.proceed(newBuilder.build())
    }

    companion object {
        val TAG = HeaderInterceptor::class.java.simpleName
    }
}