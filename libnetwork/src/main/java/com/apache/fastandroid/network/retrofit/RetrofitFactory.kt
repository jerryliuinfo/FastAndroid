package com.apache.fastandroid.network.retrofit
import com.apache.fastandroid.network.api.ApiService
import com.apache.fastandroid.network.api.FakeApi
import com.apache.fastandroid.network.calladapter.apiresult.ApiResultAdapterFactory
import com.apache.fastandroid.network.calladapter.livedata.LiveDataCallAdapterFactory
import com.apache.fastandroid.network.calladapter.networkresult.NetworkResultCallAdapterFactory
import com.apache.fastandroid.network.interceptor.AuthenticationInterceptor
import com.apache.fastandroid.network.interceptor.UserAgentInterceptor
import com.apache.fastandroid.network.util.ApiConstant
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.MediaType.Companion.toMediaType
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
     private val mRetrofit : Retrofit

     private val apiService: ApiService

     private val apiService2: ApiService

     private val okHttpClient:OkHttpClient by lazy {
         OkHttpClientManager.getOkHttpClient()
     }

    init {

        mRetrofit = newRetrofitBuilder()
            .baseUrl(ApiConstant.BASE_URL)
            .client(okHttpClient)
            .build()

        apiService = create(ApiService::class.java)

        apiService2 = create(ApiService::class.java)
    }


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







    //给 AlbumListFragment 用
    fun <Service> createAlbumService(serviceClass: Class<Service>, baseUrl: String): Service {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor("70696db59158cb100370ad30a7a705c1"))
            .addInterceptor(UserAgentInterceptor())
            .build()
        return newRetrofitBuilder()
//            .addConverterFactory()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
            .create(serviceClass)
    }




    private fun newRetrofitBuilder():Retrofit.Builder{
        val contentType = "application/json".toMediaType()


        // val json = kotlinx.serialization.json.Json {
        //     // By default Kotlin serialization will serialize all of the keys present in JSON object and throw an
        //     // exception if given key is not present in the Kotlin class. This flag allows to ignore JSON fields
        //     ignoreUnknownKeys = true
        // }


        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            //支持返回   类型的数据
            /**
             * 支持返回  [ApiResponse] 类型的数据
             */
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            //支持返回 RxJava  类型的数据
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            //支持返回 ApiResult<T> 类型的数据
            .addCallAdapterFactory(ApiResultAdapterFactory())

            //Retrofit 支持返回 LiveData 类型的数据
            .addCallAdapterFactory(LiveDataCallAdapterFactory.create())
            //添加支持返回 NetworkResult 类型的数据
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
    }






    fun apiService(): ApiService = apiService

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
            return sFakeApi?: FakeApi()
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