package com.apache.fastandroid.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.lang.Math.min

/**
 * Created by Jerry on 2023/3/27.
 */
class RetryInterceptor(private val maxRetry:Int = MAX_RETRIES ) : Interceptor {

    companion object {
        const val MAX_RETRIES = 3
        const val INITIAL_BACKOFF_MS = 1000L
        const val MAX_BACKOFF_MS = 5000L
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var response = chain.proceed(request)
        var tryCount = 0
        var backoffTimeMs = INITIAL_BACKOFF_MS

        while (!response.isSuccessful && tryCount < MAX_RETRIES) {
            tryCount++
            response.close()

            request = request.newBuilder().build()

            // 延迟一段时间后重试
            Thread.sleep(backoffTimeMs)
            backoffTimeMs = (backoffTimeMs * 2).coerceAtMost(MAX_BACKOFF_MS)

            response = chain.proceed(request)
        }

        return response
    }
}