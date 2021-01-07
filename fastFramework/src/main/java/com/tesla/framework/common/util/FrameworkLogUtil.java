package com.tesla.framework.common.util;

import com.tesla.framework.applike.FrameworkApplication;
import com.tesla.framework.common.util.log.FastLog;

/**
 * author: 01370340
 * data: 2019/6/13
 * description:
 */
public class FrameworkLogUtil {
    public static final String TAG = FrameworkLogUtil.class.getSimpleName();
    /**
     * 将日志写入到文件中 v
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void v(String format, Object... args) {
        FastLog.v(TAG,VersionUtil.getVersionNameAndFormat(FrameworkApplication.getContext(),format), args);
    }

    /**
     * 将日志写入到文件中 d
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void d(String format, Object... args) {
        FastLog.d(TAG,VersionUtil.getVersionNameAndFormat(FrameworkApplication.getContext(),format), args);
    }

    /**
     * 将日志写入到文件中 w
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void w(String format, Object... args) {
        FastLog.w(TAG,VersionUtil.getVersionNameAndFormat(FrameworkApplication.getContext(),format), args);
    }

    /**
     * 将日志写入到文件中 e
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void e(String format, Object... args) {
        FastLog.e(TAG,VersionUtil.getVersionNameAndFormat(FrameworkApplication.getContext(),format), args);
    }








}
