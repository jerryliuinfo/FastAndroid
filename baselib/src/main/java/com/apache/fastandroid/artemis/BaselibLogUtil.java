package com.apache.fastandroid.artemis;

import com.tesla.framework.common.util.VersionUtil;
import com.tesla.framework.common.util.log.NLog;

/**
 * Created by 01370340 on 2017/11/24.
 */

public class BaselibLogUtil {
    private static String getLogTag(){
        return "baseLib";
    }


    public static void d(String format, Object...args)
    {
        NLog.d(getLogTag(), VersionUtil.getVersionNameAndFormat(BaseApp.getContext(),format),args);
    }

    public static void i(String format, Object...args)
    {
        NLog.i(getLogTag(),VersionUtil.getVersionNameAndFormat(BaseApp.getContext(),format),args);
    }

    public static void v(String format, Object...args)
    {
        NLog.v(getLogTag(),VersionUtil.getVersionNameAndFormat(BaseApp.getContext(),format),args);
    }

    public static void e(String format, Object...args)
    {
        NLog.e(getLogTag(),VersionUtil.getVersionNameAndFormat(BaseApp.getContext(),format),args);
    }


}
