package com.apache.fastandroid.download;

/**
 * Created by jerryliu on 2017/6/8.
 */

public interface IDownLoadCallback {
    void onStart();
    void onProgressUpdate();
    void onSuccess();
    void onFailed();
    void onFinish();
}
