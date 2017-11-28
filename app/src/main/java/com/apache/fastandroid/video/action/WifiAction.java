package com.apache.fastandroid.video.action;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.tesla.framework.support.action.IAction;

/**
 * Created by 01370340 on 2017/10/18.
 */

public class WifiAction extends IAction {
    public WifiAction(Activity context, IAction parent) {
        super(context, parent);
    }

    private boolean isWifi = false;

    @Override
    public boolean interrupt() {
        //如果不是wifi就蓝记
        //boolean isWifi =  NetworkHelper.isWifiConnected(FrameworkApplication.getContext());
        if (!isWifi){
            doInterrupt();
        }
        return !isWifi;
    }

    @Override
    public void doInterrupt() {
        super.doInterrupt();
        //弹出对话框

        new MaterialDialog.Builder(getContext())
//                        .setTitle(R.string.profile_ad_title)
//                        .setMessage(R.string.profile_ad_message)
                .content("当前网络为非wifi环境,是否继续播放?")
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        isWifi = true;
                        run();
                    }

                })
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                    }

                })
                .show();

    }
}
