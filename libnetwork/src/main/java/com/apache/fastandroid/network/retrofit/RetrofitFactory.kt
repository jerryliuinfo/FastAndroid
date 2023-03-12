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

        okHttpClient = OkHttpClientManager.getOkHttpClient()

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
                //Retrofit 支持返回 LiveData 数据
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
    }






    fun apiService():ApiService = apiService

    private object Holder{
        val instance = RetrofitFactory()
    }

    companion object {
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