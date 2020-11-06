package com.apache.fastandroid.sample.logger;

/**
 * author: jerry
 * created on: 2020/8/4 11:51 AM
 * description:
 */
public interface InterchangeLogger {
    void w(String tag, String msg);

    void e(String tag, String msg);

    void e(String tag, Throwable e);

    void i(String tag, String msg);
}
