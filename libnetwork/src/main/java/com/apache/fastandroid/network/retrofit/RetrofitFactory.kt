package com.apache.fastandroid.network.retrofit
import android.os.Build
import com.apache.fastandroid.network.calladapter.LiveDataCallAdapterFactory
import com.apache.fastandroid.network.interceptor.CookieInterceptor
import com.apache.fastandroid.network.interceptor.NetLogInterceptor
import com.apache.fastandroid.network.interceptor.LoginInterceptor
import com.apache.fastandroid.retrofit.ApiConstant
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
            //https://t.zsxq.com/nUnEune
            if (Build.VERSION.SDK_INT >= 28) {
                pingInterval(3, TimeUnit.SECONDS)
            }

        }.apply {
//            addInterceptor(HeaderInterceptor())
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