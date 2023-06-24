package com.apache.fastandroid.network.retrofit

import android.os.Build
import com.apache.fastandroid.network.interceptor.*
import com.apache.fastandroid.network.ssl.SslClientSetup
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.linkaipeng.oknetworkmonitor.listener.NetworkEventListener
import com.linkaipeng.oknetworkmonitor.reporter.OkNetworkMonitorInterceptor
import com.mooc.libnetwork.BuildConfig
import okhttp3.*
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Jerry on 2023/3/11.
 */
object OkHttpClientManager {


    private const val CONNECTION_TIMEOUT = 10000L
    private const val READ_TIMEOUT = 30000L
    private const val MAX_CONNECTIONS = 8

    private const val TIMEOUT = 10L


    fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
            writeTimeout(READ_TIMEOUT,TimeUnit.MILLISECONDS)
            retryOnConnectionFailure(true)
            cache(Cache(File(Utils.getApp().cacheDir, "http-cache"), 10L * 1024L * 1024L)) // 10 MiB

            SslClientSetup.installCertificates(this)

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
                if (Date() < Date(2023, Calendar.OCTOBER, 7)) {
                    add("www.wanandroid.com", "sha256/Jkegy5Sc8Gjzbi65ztVCGTV90JysJOm3uRtbrclTpOc=")
                } else if (BuildConfig.DEBUG) {
                    ToastUtils.showLong("请更新证书和时间")
                }
            }.build())

        }.apply {
            addNetworkInterceptor(HeaderInterceptor())
            //处理错误码的拦截器 https://juejin.cn/post/6844903975028785159?share_token=c0d3237c-1ab3-4b7c-834f-07b502b865ea
            addInterceptor(HandleHttpCodeInterceptor())
            addInterceptor(HandleErrorInterceptor())

            addChuckerInterceptor(this)

            addNetworkInterceptor(ErrorInterceptor())
            addNetworkInterceptor(NetLogInterceptor())
//            addInterceptor(CookieInterceptor())
            addNetworkInterceptor(OkNetworkMonitorInterceptor())
//            addNetworkInterceptor(RetryInterceptor())
        }.apply {
            addNetworkInterceptor(OkNetworkMonitorInterceptor())


            addNetworkInterceptor(CacheInterceptor()) //仅当未从服务器启用 Cache-Control 标头时
            addInterceptor(ForceCacheInterceptor())


            addNetworkInterceptor(OkNetworkMonitorInterceptor())
            eventListenerFactory(NetworkEventListener.Companion.FACTORY)
        }.build()
    }


    private fun addChuckerInterceptor(build:OkHttpClient.Builder):OkHttpClient.Builder{

        if (BuildConfig.DEBUG){

        }
        val chuckerCollector = ChuckerCollector(
            context = Utils.getApp(),
            // Toggles visibility of the notification
            showNotification = true,
            // Allows to customize the retention period of collected data
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        // Create the Interceptor
        val chuckerInterceptor = ChuckerInterceptor.Builder(Utils.getApp())
            // The previously created Collector
            .collector(chuckerCollector)
            // The max body content length in bytes, after this responses will be truncated.
            .maxContentLength(250_000L)
            // List of headers to replace with ** in the Chucker UI
            .redactHeaders("Auth-Token", "Bearer")
            // Read the whole response body even when the client does not consume the response completely.
            // This is useful in case of parsing errors or when the response body
            // is closed before being read like in Retrofit with Void and Unit types.
            .alwaysReadResponseBody(true)
            // Use decoder when processing request and response bodies. When multiple decoders are installed they

            // Controls Android shortcut creation. Available in SNAPSHOTS versions only at the moment
            .build()


        build.addInterceptor(chuckerInterceptor)

        return build
    }


    fun enqueue(url: String, callback: Callback) {
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