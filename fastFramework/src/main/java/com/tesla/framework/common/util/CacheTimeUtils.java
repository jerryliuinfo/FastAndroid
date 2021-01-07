package com.tesla.framework.common.util;

import android.content.Context;
import android.text.TextUtils;

import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.common.util.sp.SPUtil;

/**
 * Created by JerryLiu on 17/04/08.
 */
public class CacheTimeUtils {

    public static void saveTime(Context context, String key) {
        String time = String.valueOf(System.currentTimeMillis() / 1000);

        SPUtil.putString(key, time);

        FastLog.d("CacheTimeUtils", String.format("保存缓存 %s, saveTime = %s", key, time));
    }

    public static long getSaveTime(Context context, String key) {
        String time = SPUtil.getString(key, "");
        long saveTime = Long.parseLong(TextUtils.isEmpty(time) ? "0" : time);
        return saveTime;
    }

    public static boolean isOutofdate(Context context, String key, long refreshInterval) {
        try {
            long saveTime = getSaveTime(context, key);

            boolean expired = Math.abs((System.currentTimeMillis() / 1000 - saveTime) * 1000) >= refreshInterval;

            FastLog.d("CacheTimeUtils", String.format("缓存有效性 %s, expired = %s", key, String.valueOf(expired)));

            return expired;
        } catch (Throwable e) {
            e.printStackTrace();

            return true;
        }
    }

}
