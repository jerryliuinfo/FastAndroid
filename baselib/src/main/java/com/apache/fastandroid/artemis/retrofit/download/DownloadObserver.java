package com.apache.fastandroid.artemis.retrofit.download;

import com.apache.fastandroid.artemis.rx.observer.CommonObserver;

import okhttp3.ResponseBody;

/**
 * Created by 01370340 on 2018/4/13.
 */

public class DownloadObserver extends CommonObserver<ResponseBody> {

    private String fileUrl;

    public DownloadObserver(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    @Override
    protected void onFailed(String errorMsg) {

    }

    @Override
    protected void onSuccess(ResponseBody T) {

    }

    @Override
    public void doOnNext(ResponseBody responseBody) {
        super.doOnNext(responseBody);



    }
}
