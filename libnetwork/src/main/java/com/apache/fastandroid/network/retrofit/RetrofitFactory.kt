package com.apache.fastandroid.network.retrofit
import com.apache.fastandroid.network.interceptor.CookieInterceptor
import com.apache.fastandroid.network.interceptor.HeaderInterceptor
import com.apache.fastandroid.network.interceptor.HttpLogInterceptor
import com.apache.fastandroid.network.interceptor.LoginInterceptor
import com.apache.fastandroid.retrofit.ApiConstant
import com.tesla.framework.kt.SPreference
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


/**
 * Created with Android Studio.
 * Description:
 * @date: 2020/02/24
 * Time: 16:56
 */

class RetrofitFactory private constructor() {
     val retrofit : Retrofit

    fun <T> create(clazz: Class<T>) : T {
        return retrofit.create(clazz)
    }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(ApiConstant.BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(initOkHttpClient())
            .build()
    }

    companion object {
        val instance by lazy {
            RetrofitFactory()
        }
    }

    private fun initOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(HttpLogInterceptor())
            .addInterceptor(CookieInterceptor())
            .addInterceptor(LoginInterceptor())
            .build()
    }
    /*private fun initLoggingIntercept(): Interceptor {
        return HttpLoggingInterceptor { message ->
            try {
                val text: String = URLDecoder.decode(message, "utf-8")
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }*/

    val apiService:ApiService = create(ApiService::class.java)

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

    private fun saveCookie(domain: String?, parseCookie: String) {
        domain?.let {
            var resutl :String by SPreference("cookie", parseCookie)
            resutl = parseCookie
        }
    }
}