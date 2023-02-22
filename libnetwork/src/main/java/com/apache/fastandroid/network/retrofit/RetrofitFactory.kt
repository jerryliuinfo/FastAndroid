package com.apache.fastandroid.network.retrofit
import android.os.Build
import com.apache.fastandroid.network.calladapter.LiveDataCallAdapterFactory
import com.apache.fastandroid.network.interceptor.*
import com.blankj.utilcode.util.ToastUtils
import com.mooc.libnetwork.BuildConfig
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit


/**
 * Created with Android Studio.
 * Description:
 * @date: 2020/02/24
 * Time: 16:56
 */

class RetrofitFactory private constructor() {
     private val mRetrofit : Retrofit

     private val apiService:ApiService

     private val apiService2:ApiService

     private val okHttpClient:OkHttpClient


    fun <T> create(clazz: Class<T>) : T {
        return mRetrofit.create(clazz)
    }

    /**
     * 指定baseUrl
     */
    fun <Service> create(serviceClass: Class<Service>, baseUrl: String): Service {
        return newRetrofitBuilder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
            .create(serviceClass)
    }

    init {

        okHttpClient = initOkHttpClient()

        mRetrofit = newRetrofitBuilder()
            .baseUrl(ApiConstant.BASE_URL)
            .client(okHttpClient)
            .build()

        apiService = create(ApiService::class.java)

        apiService2 = create(ApiService::class.java)
    }


    private fun newRetrofitBuilder():Retrofit.Builder{
        return Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
    }


    private fun initOkHttpClient(): OkHttpClient {

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
                if (Date() < Date(2023,Calendar.OCTOBER,7)){
                    add("www.wanandroid.com", "sha256/Jkegy5Sc8Gjzbi65ztVCGTV90JysJOm3uRtbrclTpOc=")
                }else if (BuildConfig.DEBUG){
                    ToastUtils.showLong("请更新证书和时间")
                }
            }.build())

        }.apply {
            addInterceptor(HeaderInterceptor())
            //处理错误码的拦截器 https://juejin.cn/post/6844903975028785159?share_token=c0d3237c-1ab3-4b7c-834f-07b502b865ea
            addInterceptor(HandleHttpCodeInterceptor())
            addInterceptor(HandleErrorInterceptor())
            addInterceptor(NetLogInterceptor())
            addInterceptor(CookieInterceptor())
            addInterceptor(LoginInterceptor())
        }.build()
    }



    fun apiService():ApiService = apiService

    private object Holder{
        val instance = RetrofitFactory()
    }

    companion object {
        private const val TIMEOUT = 10L
        val instance by lazy {
            RetrofitFactory()
        }

        fun get():RetrofitFactory{
            return Holder.instance
        }
    }





    private var sFakeApi: FakeApi? = null
    val fakeApi: FakeApi
        get() {
            return sFakeApi?:FakeApi()
        }


    private fun parseCookie(it: List<String>): String {
        if(it.isEmpty()){
            return ""
        }

        val stringBuilder = StringBuilder()

        it.forEach { cookie ->
            stringBuilder.append(cookie).append(";")
        }

        if(stringBuilder.isEmpty()){
            return ""
        }
        //末尾的";"去掉
        return stringBuilder.deleteCharAt(stringBuilder.length - 1).toString()
    }


}