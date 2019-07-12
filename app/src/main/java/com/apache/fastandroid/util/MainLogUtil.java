package com.apache.fastandroid.util;

import com.apache.fastandroid.artemis.BaseApp;
import com.tesla.framework.common.util.VersionUtil;
import com.tesla.framework.common.util.log.NLog;

/**
 * author: 01370340
 * data: 2019/6/13
 * description:
 */
public class MainLogUtil {
    public static final String TAG = MainLogUtil.class.getSimpleName();
    /**
     * 将日志写入到文件中 v
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void v(String format, Object... args) {
        NLog.v(TAG,VersionUtil.getVersionNameAndFormat(BaseApp.getContext(),format), args);
    }

    /**
     * 将日志写入到文件中 d
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void d(String format, Object... args) {
        NLog.d(TAG,VersionUtil.getVersionNameAndFormat(BaseApp.getContext(),format), args);
    }

    /**
     * 将日志写入到文件中 w
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void w(String format, Object... args) {
        NLog.w(TAG,VersionUtil.getVersionNameAndFormat(BaseApp.getContext(),format), args);
    }

    /**
     * 将日志写入到文件中 e
     *
     * @param format 格式化日志
     * @param args   格式化日志参数
     */
    public static void e(String format, Object... args) {
        NLog.e(TAG,VersionUtil.getVersionNameAndFormat(BaseApp.getContext(),format), args);
    }








}