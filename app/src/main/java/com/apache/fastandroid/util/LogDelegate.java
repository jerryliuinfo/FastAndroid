package com.apache.fastandroid.util;

import com.tesla.framework.common.util.log.FastLog;
import com.tesla.framework.common.util.log.FastLog.LogConfig;
import com.tesla.framework.common.util.log.Logger;
import com.tesla.framework.common.util.log.NLog;

/**
 * Created by Jerry on 2021/1/7.
 */
public class LogDelegate implements FastLog.IFastLogDelegate {
    private LogConfig config;


    @Override
    public void setLogConfig(LogConfig config) {
        this.config = config;
        NLog.setDebug(config.openLog, Logger.DEBUG);
    }

    @Override
    public boolean isDebug() {
        return config.openLog;
    }

    @Override
    public void e(String tag, String msg, Object... obj) {
        if (config.openLog)
        {
            NLog.e(tag, msg,obj);
        }
    }

    @Override
    public void w(String tag, String msg, Object... obj) {
        if (config.openLog)
        {
            NLog.w(tag, msg,obj);
        }
    }

    @Override
    public void i(String tag, String msg, Object... obj) {
        if (config.openLog)
        {
            NLog.i(tag, msg,obj);
        }
    }

    @Override
    public void d(String tag, String msg, Object... obj) {
        if (config.openLog)
        {
            NLog.d(tag, msg,obj);
        }
    }

    @Override
    public void v(String tag, String msg, Object... obj) {
        if (config.openLog)
        {
            NLog.v(tag, msg,obj);
        }
    }

    @Override
    public void printStackTrace(String tag, Throwable e) {
        if (config.openLog)
        {
            NLog.e(tag, e);
        }
    }


}
