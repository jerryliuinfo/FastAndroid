package com.tesla.framework.common.util.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

/**
 * Created by 01370340 on 2018/4/1.
 */

public class AlarmUtil {

    public static final String ALARM_ACTION = "com.tesla.framework.ACTION_ALARM_CLOCK";


    public static void setAlarmTime(Context context, long timeInMillis, Intent intent) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent sender = PendingIntent.getBroadcast(context, intent.getIntExtra("id", 0),
                intent, PendingIntent.FLAG_CANCEL_CURRENT);
        int interval = (int) intent.getLongExtra("intervalMillis", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, timeInMillis, interval, sender);
        }
    }

    public static void cancelAlarm(Context context, String action, int id) {
        Intent intent = new Intent(action);
        PendingIntent pi = PendingIntent.getBroadcast(context, id, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(pi);
    }


    /**
     *
     * @param context
     * @param triggerAtMilles
     * @param intervalMillis 为0 代表只执行一次闹钟
     * @param sender
     */
    public static void setAlarmNew(Context context,long triggerAtMilles,long intervalMillis, PendingIntent sender) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //4.4以后
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            am.setWindow(AlarmManager.RTC_WAKEUP, triggerAtMilles,
                    intervalMillis, sender);
        }
        //4.4以前
        else {
            //一次性闹钟
            if (intervalMillis == 0){
                am.set(AlarmManager.RTC_WAKEUP, triggerAtMilles, sender);
            }
            //重复闹钟
            else {
                am.setRepeating(AlarmManager.RTC_WAKEUP, triggerAtMilles, intervalMillis, sender);
            }
        }
    }



}
