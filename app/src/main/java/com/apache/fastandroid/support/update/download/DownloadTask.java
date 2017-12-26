package com.apache.fastandroid.support.update.download;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.apache.fastandroid.support.sdk.bean.UpdateBean;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;

import java.lang.ref.WeakReference;

/**
 * Created by 01370340 on 2017/12/24.
 */

public class DownloadTask extends WorkTask<UpdateBean,Integer,Void> {

    public static final int SHOW_DOWNLOAD_PROGRESS = 1;
    public static final int DOWNLOAD_FINISHED = 2;
    public static final int DOWNLOAD_FAILED = 3;

    DownloadHandler sDownloadHandler;
    private UpdateBean mUpdateBean;

    public DownloadTask(Activity activity,UpdateBean updateBean) {
        sDownloadHandler = new DownloadHandler(activity);
        this.mUpdateBean = updateBean;
    }



    @Override
    public Void workInBackground(UpdateBean... params) throws TaskException {
        //todo 开始下载文件


        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        Message message = Message.obtain();
        if (values[0] >= 100){
            message.what = DOWNLOAD_FINISHED;
        }else {
            message.what = SHOW_DOWNLOAD_PROGRESS;
        }
        message.obj = values[0];
        sDownloadHandler.sendMessage(message);
    }

    @Override
    protected void onFailure(TaskException exception) {
        super.onFailure(exception);
        Message message = Message.obtain();
        message.what = DOWNLOAD_FAILED;
        sDownloadHandler.sendMessage(message);
    }

    @Override
    protected void onFinished() {
        super.onFinished();
        sDownloadHandler.sendEmptyMessage(DOWNLOAD_FINISHED);
    }






    private static class DownloadHandler extends Handler {

        private WeakReference<Activity> weakReference;

        public DownloadHandler(Activity context) {
            weakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            DownloadDialog activity = (DownloadDialog) weakReference.get();
            if (activity == null || activity.isDestroyed() || activity.isFinishing() ){
                return;
            }
            switch (msg.what){
                case SHOW_DOWNLOAD_PROGRESS:
                    int progress = (int) msg.obj;
                    activity.showDownloadProgress(progress);
                    break;
                case DOWNLOAD_FINISHED:
                    //下载完成
                    activity.downloadFinished();

                    break;
                case DOWNLOAD_FAILED:
                    activity.downloadFailed();
                    break;

                default:
                    break;
            }
        }
    }

}
