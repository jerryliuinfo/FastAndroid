package com.apache.fastandroid.performance;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;

import com.apache.fastandroid.BuildConfig;
import com.apache.fastandroid.R;
import com.apache.fastandroid.app.FastApplication;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.NotificationUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.github.moduth.blockcanary.internal.BlockInfo;
import com.github.moduth.blockcanary.ui.DisplayActivity;
import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.util.log.NLog;

import java.util.List;

import androidx.core.app.NotificationCompat;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by Jerry on 2021/6/28.
 */
public class AppBlockCanaryContext  extends BlockCanaryContext {
    private static final String TAG = "AppContext";

    @Override
    public String provideQualifier() {
        String qualifier = "";
        try {
            PackageInfo info = FastApplication.getContext().getPackageManager()
                    .getPackageInfo(FastApplication.getContext().getPackageName(), 0);
            qualifier += info.versionCode + "_" + info.versionName + "_YYB";
        } catch (PackageManager.NameNotFoundException e) {
            NLog.e(TAG, "provideQualifier exception", e);
        }
        return qualifier;
    }

    @Override
    public String provideUid() {
        return "87224330";
    }

    @Override
    public String provideNetworkType() {
        return "4G";
    }

    /**
     *
     * @return
     */
    @Override
    public int provideMonitorDuration() {
        return 9999;
    }

    /**
     * 线程阻塞开始，每500ms采集一次数据
     * @return
     */
    @Override
    public int provideBlockThreshold() {
        return 500;
    }

    @Override
    public boolean displayNotification() {
        return BuildConfig.DEBUG;
    }

    @Override
    public List<String> concernPackages() {
        List<String> list = super.provideWhiteList();
        list.add("com.example");
        return list;
    }

    @Override
    public List<String> provideWhiteList() {
        List<String> list = super.provideWhiteList();
        list.add("com.whitelist");
        return list;
    }

    @Override
    public boolean stopWhenDebugging() {
        return true;
    }

    @Override
    public void onBlock(Context context, BlockInfo blockInfo) {
        super.onBlock(context, blockInfo);
        NLog.e(TAG, "onBlock : %s",blockInfo);

        String path = FrameworkApplication.getApplication().getExternalFilesDir("block").getAbsolutePath();
        NLog.e(TAG, "onBlock path: %s, blockInfo: %s",path,blockInfo);
        NotificationUtils.notify(100, new Utils.Consumer<NotificationCompat.Builder>() {
            @Override
            public void accept(NotificationCompat.Builder builder) {
                NLog.e(TAG, "onBlock  show notify accept");
                String contentTitle = context.getString(com.github.moduth.blockcanary.R.string.block_canary_class_has_blocked, blockInfo.timeStart);
                String contentText = context.getString(com.github.moduth.blockcanary.R.string.block_canary_notification_message);
                builder.setSmallIcon(R.drawable.block_canary_notification)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.block_canary_notification))
                        .setContentTitle(contentTitle)
                        .setContentText(contentText)
                        .setAutoCancel(true)
                        .setProgress(0, 0, false);// Removes the progress bar

                Intent intent = new Intent(context, DisplayActivity.class);
                intent.putExtra("show_latest", blockInfo.timeStart);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, intent, FLAG_UPDATE_CURRENT);


                if (pendingIntent != null) {
                    builder.setContentIntent(pendingIntent);
                } else {
                    builder.setContentIntent(PendingIntent.getBroadcast(context, 0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
                }
            }
        });
        ToastUtils.showLong("卡顿信息已保存在"+ path);
        FileIOUtils.writeFileFromString(path,blockInfo.toString() );
    }
}
