package com.apache.fastandroid.artemis;

import com.tesla.framework.common.util.log.FastLog;

/**
 * Created by 01370340 on 2017/11/24.
 */

public class LibraryLog {
    public static String getLogTag(){
        return "library";
    }


    public static void d(String format, Object...args)
    {
        FastLog.d(getLogTag(),format,args);
    }

    public static void i(String format, Object...args)
    {
        FastLog.i(getLogTag(),format,args);
    }

    public static void v(String format, Object...args)
    {
        FastLog.v(getLogTag(),format,args);
    }

    public static void e(String format, Object...args)
    {
        FastLog.e(getLogTag(),format,args);
    }


}
