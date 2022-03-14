package com.apache.fastandroid.network.interceptor

import android.text.TextUtils
import com.apache.fastandroid.network.retrofit.ApiEngine
import com.blankj.utilcode.util.SPUtils
import com.tesla.framework.common.util.log.NLog
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Jerry on 2021/11/8.
 */
class TokenInterceptor(private var token:String):Interceptor {
    companion object{
        private const val TAG = "TokenInterceptor"
        private val spUtils = SPUtils.getInstance("userInfo")
        private const val KEY_TOKEN_TIME = "tokenTime"
        private const val KEY_REFRESH_TOKEN = "tokenTime"
    }
    override fun intercept(chain: Interceptor.Chain): Response {
        //当前时间
        val nowTime = System.currentTimeMillis()
        //登录成功时的时间-当前时间得到时间差
        val timeDifference = spUtils.getLong(KEY_TOKEN_TIME, 0) - nowTime
        if ((TextUtils.isEmpty(spUtils.getString("token"))) or (timeDifference > 86399)) {
            //如果access_token为空,或者 access_token有效期超过24小时,需要刷新token
            //获取新的token
            val newToken = getNewToken()
            spUtils.put(KEY_TOKEN_TIME, System.currentTimeMillis())
            NLog.d(TAG, "newToken: ${newToken}" )
            val newRequest = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $newToken")
                .build()
            return chain.proceed(newRequest)
        } else {
            val oldRequest = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            return chain.proceed(oldRequest)
        }
    }

    private fun getNewToken(): String? {
        var newToken: String?
        var refreshToken: String?
        synchronized(TokenInterceptor::class.java) {
            val refresh = spUtils.getLong(KEY_REFRESH_TOKEN, 0)

            //刷新access_token的接口请求
            val refreshTokenR = ApiEngine.apiService
            val call = refreshTokenR.refreshToken(refresh)
            val execute = call.execute()
            newToken = execute.body()!!.data?.access_token
            refreshToken = execute.body()!!.data?.refresh_token
            spUtils.put(KEY_REFRESH_TOKEN, refreshToken)
        }
        return newToken
    }

}