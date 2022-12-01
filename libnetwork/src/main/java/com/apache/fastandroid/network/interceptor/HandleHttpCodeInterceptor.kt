package com.apache.fastandroid.network.interceptor

import com.apache.fastandroid.network.exception.ApiException
import okhttp3.Response

/**
 * Created by Jerry on 2022/11/30.
 * 直接判断 http 请求的状态码
 */
class HandleHttpCodeInterceptor : ResponseBodyInterceptor() {

    override fun intercept(response: Response, url: String, body: String): Response {
        when (response.code) {
            600,601,602 -> {
                throw ApiException(response.code, "msg")
            }
            else -> {
            }
        }
        return response
    }
}