package com.apache.fastandroid.network.interceptor

import android.os.Build

import okhttp3.*
import java.io.IOException
import java.lang.Exception



/**
 * Created by Jerry on 2021/3/23.
 */
class LoginInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.request().newBuilder()
            .addHeader("brand", Build.BRAND)
            .build()
            .let {
            chain.proceed(it)
        }

    companion object {
        val TAG = LoginInterceptor::class.java.simpleName
    }
}