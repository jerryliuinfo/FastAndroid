package com.apache.fastandroid.support.update.download;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.apache.fastandroid.support.sdk.bean.UpdateBean;
import com.tesla.framework.ui.activity.BaseActivity;

import java.lang.ref.WeakReference;

/**
 * Created by 01370340 on 2017/12/24.
 */

public class DownloadDialog extends BaseActivity {

    public static final int SHOW_UPDATE_PROGRESS = 1;
    public static final int SHOW_UPDATE_FINISHED = 2;
    public static final int SHOW_UPDATE_FAILED = 3;
    private static final String EXTRA_UPDATE_BEAN = "EXTRA_UPDATE_BEAN";

    private UpdateBean mUpdateBean;

    public static void launch(Activity from, UpdateBean bean) {
        Intent intent = new Intent(from, DownloadDialog.class);
        intent.putExtra(EXTRA_UPDATE_BEAN,bean);
        from.startActivity(intent);
    }

    public static class DownloadHandler extends Handler {

        private WeakReference<Activity> weakReference;

        public DownloadHandler(Activity context) {
            weakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Activity activity = weakReference.get();
            if (activity == null || activity.isDestroyed() || activity.isFinishing() ){
                switch (msg.what){
                    case SHOW_UPDATE_PROGRESS:

                        break;
                    case SHOW_UPDATE_FINISHED:

                        break;
                    case SHOW_UPDATE_FAILED:
                        Toast.makeText(activity, "下载失败", Toast.LENGTH_LONG).show();
                        break;

                    default:
                        break;
                }
            }
        }
    }
    private DownloadHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null){
            mUpdateBean = (UpdateBean) getIntent().getSerializableExtra(EXTRA_UPDATE_BEAN);
        }else {
            mUpdateBean = (UpdateBean) savedInstanceState.getSerializable(EXTRA_UPDATE_BEAN);
        }
        handler = new DownloadHandler(this);
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.setDownloadData(handler,mUpdateBean);
        downloadTask.execute();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_UPDATE_BEAN,mUpdateBean);
    }
}
