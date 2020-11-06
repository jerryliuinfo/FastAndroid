package com.apache.fastandroid.sample.logger;

/**
 * author: jerry
 * created on: 2020/8/4 11:56 AM
 * description:
 */
public interface Logger {
    void w(String tag, String msg);

    void e(String tag, String msg);

    void e(String tag, Throwable e);

    void i(String tag, String msg);

    void d(String tag, String msg);
}
