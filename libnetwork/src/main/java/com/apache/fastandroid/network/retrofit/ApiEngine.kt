package com.apache.fastandroid.network.retrofit

import android.os.Build
import retrofit2.Retrofit
import com.apache.fastandroid.network.retrofit.convertor.CustomGsonConverterFactory
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import com.apache.fastandroid.retrofit.ApiConstant
import com.blankj.utilcode.util.ToastUtils
import com.mooc.libnetwork.BuildConfig
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

object ApiEngine {
    private val car = Any()
    private val sword = Any()
    const val TAG = "ApiEngine"
    private const val CONN_TIMEOUT = 10000
    private const val READ_TIMEOUT = 10000
    @JvmStatic
    fun main(args: Array<String>) {
    }

    private var sOkHttpClient: OkHttpClient? = null
    val okHttpClient: OkHttpClient
        get() {
            return sOkHttpClient?:onOkHttpClientCreated()
        }
    private var sRetrofit: Retrofit? = null
    private val retrofit: Retrofit
        private get() {
            if (sRetrofit == null) {
                val builder = Retrofit.Builder()
                sRetrofit = builder
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(okHttpClient)
                    .baseUrl(ApiConstant.BASE_URL)
                    .build()
            }
            return sRetrofit!!
        }

    private fun onOkHttpClientCreated():OkHttpClient {
        val builder = OkHttpClient().newBuilder()
        builder.connectTimeout(CONN_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .retryOnConnectionFailure(true)
        //https://t.zsxq.com/nUnEune
        if (Build.VERSION.SDK_INT >= 28) {
            builder.pingInterval(3, TimeUnit.SECONDS)
        }

       /* builder.certificatePinner(CertificatePinner.Builder()
            .apply {
                if (Date() < Date(2022,Calendar.OCTOBER,7)){
                    add("https://www.wanandroid.com","sha256/AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=\"")
                }else if (BuildConfig.DEBUG){
                    ToastUtils.showShort("请更新证书和实践")
                }
            }
            .build())*/

        val interceptors: MutableList<Interceptor> = builder.interceptors()
        interceptors.add(HeaderInterceptor())
        interceptors.add(ErrorInterceptor())
        val logInterceptor = HttpLogInterceptor()
        interceptors.add(logInterceptor)
        return builder.build()
    }

    fun <T> getService(cls: Class<T>?): T {
        val retrofit = retrofit
        return retrofit!!.create(cls)
    }

    private var sApiService: ApiService? = null
    private var sApiServiceKt: ApiServiceKt? = null
    val apiService: ApiService
        get() {
            if (sApiService == null) {
                sApiService = retrofit!!.create(ApiService::class.java)
            }
            return retrofit!!.create(ApiService::class.java)
        }
    val apiServiceKt: ApiServiceKt
        get() {
            if (sApiServiceKt == null) {
                sApiServiceKt = retrofit.create(ApiServiceKt::class.java)
            }
            return retrofit!!.create(ApiServiceKt::class.java)
        }
    private var sFakeApi: FakeApi? = null
    val fakeApi: FakeApi?
        get() {
            if (sFakeApi == null) {
                sFakeApi = FakeApi()
            }
            return sFakeApi
        }
}