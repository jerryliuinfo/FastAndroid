package com.apache.fastandroid.support.update;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.apache.fastandroid.support.sdk.bean.UpdateBean;
import com.tesla.framework.common.util.view.ViewUtils;
import com.tesla.framework.network.task.TaskException;

import java.lang.ref.WeakReference;

/**
 * Created by 01370340 on 2017/12/24.
 */

public class HandleUpdateResult implements Runnable {

    private static final int CHECK_SHOW_PREGRESS_DIALOG = 0;
    private static final int CHECK_HAS_NEW_VERSION = 1;
    private static final int CHECK_NO_NEW_VERSION = 2;
    private static final int CHECK_FAILED = 3;
    private static final int CHECK_VERSION_FINISHED = 4;



    public static final int SHOW_UPDATE_DIALOG_REQUEST_CODE = 100;
    private UpdateHandler mUpdateHandler;

    public HandleUpdateResult(Activity activity) {
        mUpdateHandler = new UpdateHandler(activity);
    }

    @Override
    public void run() {
        CheckVersionTask task = new CheckVersionTask();
        task.setCallback(new CheckVersionCallback());
        task.execute();
    }

    private static class UpdateHandler extends Handler{
        private WeakReference<Activity> weakReference;

        public UpdateHandler(Activity context) {
            weakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Activity activity = weakReference.get();
            if (activity == null || activity.isDestroyed() || activity.isFinishing() ){
                switch (msg.what){
                    case CHECK_SHOW_PREGRESS_DIALOG:
                        ViewUtils.createProgressDialog(activity,"正在检测新版本，请稍后").show();
                        break;
                    case CHECK_HAS_NEW_VERSION:
                        //弹出更新版本的对话框
                        UpdateBean bean = (UpdateBean) msg.obj;
                        UpdateDialog.launchForResult(activity,SHOW_UPDATE_DIALOG_REQUEST_CODE,bean);
                        break;
                    case CHECK_NO_NEW_VERSION:
                        Toast.makeText(activity, "当前已是最新版本", Toast.LENGTH_LONG).show();
                        break;
                    case CHECK_FAILED:
                        Toast.makeText(activity, "网络不畅通", Toast.LENGTH_LONG).show();
                        break;
                    case CHECK_VERSION_FINISHED:
                        ViewUtils.dismissProgressDialog();
                        break;

                    default:

                        break;
                }
            }
        }
    }

    class CheckVersionCallback implements ICheckVersion{

        @Override
        public void onCheckPrepare() {
            mUpdateHandler.sendEmptyMessage(CHECK_SHOW_PREGRESS_DIALOG);
        }

        @Override
        public void onNewVersion(UpdateBean updateBean) {
            Message message = Message.obtain();
            message.what = CHECK_HAS_NEW_VERSION;
            message.obj = updateBean;
            mUpdateHandler.sendMessage(message);
        }

        @Override
        public void onNoNewVersion() {
            mUpdateHandler.sendEmptyMessage(CHECK_NO_NEW_VERSION);

        }

        @Override
        public void onCheckFailed(TaskException exception) {
            mUpdateHandler.sendEmptyMessage(CHECK_FAILED);
        }

        @Override
        public void onCheckFinished() {
            mUpdateHandler.sendEmptyMessage(CHECK_VERSION_FINISHED);

        }
    }
}
