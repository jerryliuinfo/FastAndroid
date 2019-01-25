package com.apache.fastandroid.topic.support;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by 01370340 on 2017/9/27.
 */

public class TopicLogUtil {
    public static String getLogTag(){
        return "topic";
    }



    private TopicLogUtil() {
    }



    /**
     * 将日志写入到文件中 v
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void v(String format, Object... args) {
        NLog.v(getLogTag(),format, args);
    }

    /**
     * 将日志写入到文件中 d
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void d(String format, Object... args) {
        NLog.d(getLogTag(),format, args);

    }
    public static void i(String format, Object... args) {
        NLog.i(getLogTag(),format, args);

    }



    /**
     * 将日志写入到文件中 e
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void e(String format, Object... args) {
        NLog.e(getLogTag(),format, args);
    }

    /**
     * 将日志写入到文件中 e
     *
     * @param tr
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void e(Throwable tr, String format, Object... args) {
        NLog.printStackTrace(tr);

    }
}
