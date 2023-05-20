package com.apache.fastandroid.network.retrofit

import android.os.Build
import com.apache.fastandroid.network.interceptor.*
import com.blankj.utilcode.util.ToastUtils
import com.linkaipeng.oknetworkmonitor.listener.NetworkEventListener
import com.linkaipeng.oknetworkmonitor.reporter.OkNetworkMonitorInterceptor
import com.mooc.libnetwork.BuildConfig
import okhttp3.Callback
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Jerry on 2023/3/11.
 */
object OkHttpClientManager {

    private const val TIMEOUT = 10L


    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            readTimeout(TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            //https://t.zsxq.com/nUnEune: 优化 android 9.0 上 http 请求的 SocketTimeoutException
            if (Build.VERSION.SDK_INT >= 28) {
                pingInterval(3, TimeUnit.SECONDS)
            }
            /**
             * https://t.zsxq.com/0aruxCqjq
             * 低成本实现网络请求安全方法，例如我们服务器的过期使劲是 2023 年 11 月，我们可以在 2023 年 10 月之前
             * 总是检查证书的 Pinning，而过了这个时间就不检查证书的 Pining，当证书过期后我们可以放心的进行更换服务端证书
             */
            certificatePinner(CertificatePinner.Builder().apply {
                if (Date() < Date(2023, Calendar.OCTOBER,7)){
                    add("www.wanandroid.com", "sha256/Jkegy5Sc8Gjzbi65ztVCGTV90JysJOm3uRtbrclTpOc=")
                }else if (BuildConfig.DEBUG){
                    ToastUtils.showLong("请更新证书和时间")
                }
            }.build())

        }.apply {
            addNetworkInterceptor(HeaderInterceptor())
            //处理错误码的拦截器 https://juejin.cn/post/6844903975028785159?share_token=c0d3237c-1ab3-4b7c-834f-07b502b865ea
            addNetworkInterceptor(HandleHttpCodeInterceptor())
            addNetworkInterceptor(HandleErrorInterceptor())
            addNetworkInterceptor(NetLogInterceptor())
            addNetworkInterceptor(CookieInterceptor())
            addNetworkInterceptor(OkNetworkMonitorInterceptor())
//            addNetworkInterceptor(RetryInterceptor())
        }.apply {
            addNetworkInterceptor(OkNetworkMonitorInterceptor())
            eventListenerFactory(NetworkEventListener.Companion.FACTORY)
        }.build()
    }


    fun enqueue(url:String, callback: Callback){
        val okHttpClient: OkHttpClient = OkHttpClient.Builder()
            .eventListenerFactory(NetworkEventListener.Companion.FACTORY)
            .addNetworkInterceptor(OkNetworkMonitorInterceptor())
            .build()

        val request: Request = Request.Builder()
            .url(url)
            .build()

        okHttpClient.newCall(request).enqueue(callback)
    }
}