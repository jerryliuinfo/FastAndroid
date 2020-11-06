package com.apache.fastandroid.sample.logger;

import android.util.Log;

/**
 * author: jerry
 * created on: 2020/8/4 11:57 AM
 * description:
 */
public class LogTool {
    private static final String TAG = "[DESIGN]-";
    private static final Logger DELEGATE;

    static {
        DELEGATE = KKInterchange.DESIGN_IN_DEBUG ? new DebugLogger() : KKInterchange.getDelegateLogger();
    }

    public static void w(String tag, String msg) {
        DELEGATE.w(TAG + tag, msg);
    }

    public static void e(String tag, String msg) {
        DELEGATE.e(TAG + tag, msg);
    }

    public static void e(String tag, Throwable e) {
        DELEGATE.e(TAG + tag, e);
    }

    public static void i(String tag, String msg) {
        DELEGATE.i(TAG + tag, msg);
    }

    public static void d(String tag, String msg) {
        DELEGATE.d(tag, msg);
    }

    private static class DebugLogger implements Logger {

        @Override
        public void w(String tag, String msg) {
            Log.w(tag, msg);
        }

        @Override
        public void e(String tag, String msg) {
            Log.e(tag, msg);
        }

        @Override
        public void e(String tag, Throwable e) {
            Log.e(tag, "", e);
        }

        @Override
        public void i(String tag, String msg) {
            Log.i(tag, msg);
        }

        @Override
        public void d(String tag, String msg) {
            Log.d(tag, msg);
        }
    }
}
