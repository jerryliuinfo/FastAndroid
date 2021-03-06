package com.apache.fastandroid;

import android.util.Log;

/**
 * Created by liujian on 2018/4/11.
 */

public class LogUtils {

    public static final String TAG = LogUtils.class.getSimpleName();
    /**
     * 将日志写入到文件中 v
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void v(String format, Object... args) {
        Log.v(TAG, buildWholeMessage(format,args));
    }

    /**
     * 将日志写入到文件中 d
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void d(String format, Object... args) {
        Log.d(TAG, buildWholeMessage(format,args));
    }

    private static String buildWholeMessage(String format, Object...args)
    {
        if (args == null || args.length == 0){
            return format;
        }
        String msg = String.format(format, args);
        return msg;
    }

    /**
     * 将日志写入到文件中 w
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void w(String format, Object... args) {

    }

    /**
     * 将日志写入到文件中 e
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void e(String format, Object... args) {
        Log.e(TAG, buildWholeMessage(format,args));

    }
}
