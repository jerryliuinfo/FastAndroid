package com.apache.fastandroid.artemis.retrofit.download;

import com.apache.fastandroid.artemis.rx.TransformerHelper;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 01370340 on 2018/4/13.
 * 为下载单独建一个retrofit
 */

public class DownloadRetrofit {
    private static volatile DownloadRetrofit instance = null;

    private Retrofit mRetrofit;
    private static String BASE_URL = "https://api.github.com/";


    private DownloadRetrofit(){
        mRetrofit = new Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();


    }
    public static DownloadRetrofit getInstance() {
        if (instance == null) {
            synchronized (DownloadRetrofit.class) {
                if (instance == null){
                    instance = new DownloadRetrofit();
                }
            }
        }
        return instance;
    }

    public Retrofit getRetrofit(){
        return mRetrofit;
    }


    public static Observable<ResponseBody> downloadFile(String fileUrl){
        return getInstance().getRetrofit().create(DownloadApi.class).downloadFile(fileUrl).compose(TransformerHelper.<ResponseBody>switchSchedulers());
    }

}
