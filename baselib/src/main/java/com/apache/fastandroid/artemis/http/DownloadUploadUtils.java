package com.apache.fastandroid.artemis.http;

import com.apache.fastandroid.artemis.retrofit.download.DownloadRetrofit;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Created by 01370340 on 2018/4/14.
 */

public class DownloadUploadUtils {

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     */
    public static Observable<ResponseBody> downloadFile(String fileUrl) {
        return downloadFile(fileUrl,null);
    }

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     */
    public static Observable<ResponseBody> downloadFile(String fileUrl,String fileName) {
        return DownloadRetrofit.downloadFile(fileUrl);
    }

}
