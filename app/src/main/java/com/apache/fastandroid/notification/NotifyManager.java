package com.apache.fastandroid.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.apache.fastandroid.MainActivity;
import com.apache.fastandroid.R;

import androidx.core.app.NotificationCompat;

/**
 * Created by Jerry on 2020/12/28.
 */
public class NotifyManager {
    // 单例开始
    private volatile static NotifyManager INSTANCE;

    private NotifyManager(Context context) {
        initNotifyManager(context);
    }

    public static NotifyManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (NotifyManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NotifyManager(context);
                }
            }
        }
        return INSTANCE;
    }
    // 单例结束

    private NotificationManager manager;
    // NotificationManagerCompat
    private NotificationCompat.Builder builder;

    //初始化通知栏配置
    private void initNotifyManager(Context context) {
        context = context.getApplicationContext();
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 如果存在则清除上一个消息
//        manager.cancel(news_flag);
        builder = new NotificationCompat.Builder(context,NotifyCompatYc.QFMD_CHANNEL_ID);

        NotifyCompatYc.setONotifyChannel(manager,builder,NotifyCompatYc.QFMD_CHANNEL_ID,NotifyCompatYc.QFMD_CHANNEL_NAME);

        // 设置标题
        builder.setContentTitle("我是通知栏标题");
        // 状态栏的动画提醒语句
        builder.setTicker("我是通知栏Ticker");
        // 什么时候提醒的
        builder.setWhen(System.currentTimeMillis());
        // 设置通知栏的优先级
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        // 设置点击可消失
        builder.setAutoCancel(true);
        // 设置是否震动等
        builder.setDefaults(Notification.DEFAULT_VIBRATE);
        // 设置icon
        builder.setSmallIcon(R.mipmap.ic_launcher);
        // 设置点击意图
        Intent intent = new Intent(context, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean("goToMyApp", true);
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 230, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
    }

    /**
     * 显示祈福明灯过期通知
     */
    public void showQiFuLampOutOfDateNotify(Context context,String title,String content) {
        // 设置内容
        builder.setContentText(content);
        manager.notify(13251, builder.build());
    }

    public void showQiFuLampBlessNotify(Context context) {
        builder.setContentText("Notify Content2");
        manager.notify(13255, builder.build());
    }
}
