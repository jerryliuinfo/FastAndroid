package com.apache.fastandroid.support.update.download;

import android.os.Message;

import com.apache.fastandroid.support.sdk.bean.UpdateBean;
import com.tesla.framework.network.task.TaskException;
import com.tesla.framework.network.task.WorkTask;

import static com.apache.fastandroid.support.update.download.DownloadDialog.SHOW_UPDATE_FAILED;

/**
 * Created by 01370340 on 2017/12/24.
 */

public class DownloadTask extends WorkTask<UpdateBean,Integer,Void> {

    private DownloadDialog.DownloadHandler mDownloadHander;
    private UpdateBean mUpdateBean;

    public void setDownloadData(DownloadDialog.DownloadHandler downloadHander,UpdateBean updateBean){
        this.mDownloadHander = downloadHander;
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
            message.what = DownloadDialog.SHOW_UPDATE_FINISHED;
        }else {
            message.what = DownloadDialog.SHOW_UPDATE_PROGRESS;
        }
        message.obj = values[0];
        mDownloadHander.sendMessage(message);
    }

    @Override
    protected void onFailure(TaskException exception) {
        super.onFailure(exception);
        Message message = Message.obtain();
        message.what = SHOW_UPDATE_FAILED;
        mDownloadHander.sendMessage(message);
    }
}
