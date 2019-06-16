package com.apache.fastandroid.artemis.retrofit;

import com.apache.fastandroid.artemis.http.GlobalHttp;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.tesla.framework.network.http.HttpClient;
import com.tesla.framework.network.task.TaskException;
import java.io.IOException;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 01370340 on 2018/4/11.
 */

public class RetrofitClient {
    private static volatile RetrofitClient instance = null;
    private Retrofit.Builder mRetrofitBuilder;
    private OkHttpClient.Builder mOkHttpBuilder;

    private RetrofitClient(){
        mOkHttpBuilder = HttpClient.getInstance().getBuilder();
        mRetrofitBuilder = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create());
        mRetrofitBuilder.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }
    public static RetrofitClient getInstance() {
        if (instance == null) {
            synchronized (RetrofitClient.class) {
                if (instance == null){
                    instance = new RetrofitClient();
                }
            }
        }
        return instance;
    }

    /**
     *  获取全局的Retrofit.Builder对象
     * @return
     */
    public Retrofit.Builder getRetrofitBuilder(){
        return mRetrofitBuilder;
    }

    /**
     * 获取全局的Retrofit对象
     * @return
     */
    public Retrofit getRetrofit(){
        return mRetrofitBuilder.client(mOkHttpBuilder.build()).build();
    }



    public static Retrofit.Builder newBuilder(){
        return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create());
    }




    /**
     * 对Retrofit的call做数据解析
     * @param call
     * @param <T>
     * @return
     * @throws TaskException
     */
    public static <T> T checkCallResult(Call<T> call) throws TaskException {
        try {
            Response<T> response = call.execute();
            if (!response.isSuccessful()){
                throw new TaskException("response.isSuccessful() = false");
            }
            T t =  response.body();
            if (t == null){
                throw new TaskException("response body is null");
            }
            return t;
        } catch (IOException e) {
            e.printStackTrace();
            throw new TaskException(e.getMessage());
        }
    }







    /****************************Retrofit相关*****************************************/

    /**
     * 设置baseUrl
     * @param baseUrl
     * @return
     */
    public static GlobalHttp setBaseUrl(String baseUrl){
        getGlobalRetrofitBuilder().baseUrl(baseUrl);
        return GlobalHttp.getInstance();
    }

    /**
     * 全局的 retrofit
     * @return
     */
    public static Retrofit getGlobalRetrofit(){
        return RetrofitClient.getInstance().getRetrofit();
    }

    /**
     * 全局的 RetrofitBuilder
     * @return
     */
    public static Retrofit.Builder getGlobalRetrofitBuilder(){
        return RetrofitClient.getInstance().getRetrofitBuilder();
    }

    /**
     * 使用全局变量的请求, 非全局变量请求不要用这个方法
     *
     * @param clz
     * @param <K>
     * @return
     */
    public static <K> K createApi(Class<K> clz ){
        return getGlobalRetrofit().create(clz);
    }


    /****************************Retrofit相关*****************************************/
}
