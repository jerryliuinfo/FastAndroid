package com.apache.fastandroid.artemis.retrofit.download;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Created by 01370340 on 2018/4/13.
 * 基于rxjava和retrofit的文件下载
 */

public interface DownloadApi {

    /**
     * 大文件官方建议用 @Streaming 来进行注解，不然会出现IO异常，小文件可以忽略不注入
     * @param fileUrl   下载地址
     * @return
     */

    @Streaming
    @GET
    Observable<ResponseBody> downloadFile(String fileUrl);
}
