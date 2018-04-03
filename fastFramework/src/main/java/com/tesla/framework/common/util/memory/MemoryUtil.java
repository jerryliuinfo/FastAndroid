package com.tesla.framework.common.util.memory;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import com.alibaba.fastjson.util.IOUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 01370340 on 2018/3/17.
 */

public class MemoryUtil {


    private static long getTotalRAM2() {
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            String line = reader.readLine();

            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(line);
            String value = "";
            while (m.find()) {
                value = m.group(1);
            }
            try {
                return Long.parseLong(value) / 1024;//KB转换成M
            } catch (NumberFormatException e) {
            }
        } catch (IOException e) {
        } finally {
            IOUtils.close(reader);
        }

        return 0;
    }

    /**
     * 获取总内存，单位M
     *
     * @param context
     * @return
     */
    public static long getTotalRAM(Context context) {
        if (Build.VERSION.SDK_INT >= 16) {
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(memoryInfo);
            return memoryInfo.totalMem / _1M;

        } else {
            return getTotalRAM2();
        }
    }


    private static long _1M = 1024 * 1024;


    /**
     * 获取当前已用内存，单位M
     *
     * @param context
     * @return
     */
    public static long getCurrentRAM(Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        return getTotalRAM(context) - memoryInfo.availMem / _1M;
    }
}
