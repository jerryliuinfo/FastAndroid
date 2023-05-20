package com.apache.fastandroid.demo.showcase.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response  {
        val it = chain.request()
        val url = it.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .addQueryParameter("format", "json")
            .build()

        val newRequest = it.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}
