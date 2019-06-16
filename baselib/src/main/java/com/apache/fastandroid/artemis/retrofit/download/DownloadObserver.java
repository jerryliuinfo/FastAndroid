package com.apache.fastandroid.artemis.retrofit.download;

import android.annotation.SuppressLint;
import com.apache.fastandroid.artemis.BaselibLogUtil;
import com.apache.fastandroid.artemis.rx.observer.CommonObserver;
import com.tesla.framework.common.util.log.NLog;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by 01370340 on 2018/4/13.
 */

public abstract class DownloadObserver extends CommonObserver<ResponseBody> {

    private String destDownloadDir;
    private String destFileName;

    public DownloadObserver(String destDownloadDir, String destFileName) {
        this.destDownloadDir = destDownloadDir;
        this.destFileName = destFileName;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onSuccess(ResponseBody t) {
        Observable.just(t).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(@NonNull ResponseBody responseBody) throws Exception {
                        try {
                            new Downloader().downloadFile(responseBody, destDownloadDir,destFileName, new ProgressListener() {
                                @Override
                                public void onResponseProgress(final long bytesRead, final long contentLength, final int progress, final boolean done, final String filePath) {
                                    Observable.just(progress).distinctUntilChanged().observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<Integer>() {
                                                @Override
                                                public void accept(@NonNull Integer integer) throws Exception {
                                                    BaselibLogUtil.d("accept integer = %s", integer);
                                                    success(bytesRead,contentLength,progress,done,filePath);
                                                }
                                            });
                                }
                            });
                        }catch (Exception e){
                            NLog.printStackTrace(e);
                            doOnError(e.getMessage());
                        }

                    }
                });
    }



    public abstract void success(long bytesRead, long contentLength, int progress, boolean done, String filePath);
}
