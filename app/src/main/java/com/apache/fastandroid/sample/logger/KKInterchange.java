package com.apache.fastandroid.sample.logger;

import android.util.Log;

import com.apache.fastandroid.BuildConfig;

import androidx.annotation.Nullable;

/**
 * author: jerry
 * created on: 2020/8/4 11:53 AM
 * description:
 */
public class KKInterchange {
    private static InterchangeLogger mLogger;
    private static final Logger DELEGATE_LOGGER = new WeakDelegateLogger();
    // 设计库是否处于DEBUG状态
    public static final boolean DESIGN_IN_DEBUG = BuildConfig.DEBUG;
    // APP项目是否处于DEBUG状态
    public static boolean APP_IN_DEBUG = DESIGN_IN_DEBUG;

    public static void setLogger(InterchangeLogger logger) {
        mLogger = logger;
    }
    /**
     * 获取内部代理到主项目的日志打印器
     *
     * @return Logger
     */
    public static Logger getDelegateLogger() {
        return DELEGATE_LOGGER;
    }



    private static final class WeakDelegateLogger implements Logger {
        @Nullable
        private InterchangeLogger getInterchangeLogger() {
            return mLogger;
        }

        @Override
        public void w(String tag, String msg) {
            InterchangeLogger logger = getInterchangeLogger();
            if (logger != null) {
                logger.w(tag, msg);
            }
        }

        @Override
        public void e(String tag, String msg) {
            InterchangeLogger logger = getInterchangeLogger();
            if (logger != null) {
                logger.e(tag, msg);
            }
        }

        @Override
        public void e(String tag, Throwable e) {
            InterchangeLogger logger = getInterchangeLogger();
            if (logger != null) {
                logger.e(tag, e);
            }
        }

        @Override
        public void i(String tag, String msg) {
            InterchangeLogger logger = getInterchangeLogger();
            if (logger != null) {
                logger.i(tag, msg);
            }
        }

        @Override
        public void d(String tag, String msg) {
            if (APP_IN_DEBUG) {
                Log.d(tag, msg);
            }
        }
    }


}
