package com.tesla.framework.common.util.io;

import android.database.Cursor;

import java.io.Closeable;
import java.io.IOException;
import java.util.zip.ZipFile;

/**
 * 和关闭流有关的帮助类
 */
public class IOUtils {
    /**
     * 大部分Close关闭流，以及实现Closeable的功能可使用此方法
     *
     * @param c Closeable对象，包括Stream等
     */
    public static void closeQuietly(Closeable c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    /**
     * 允许“一口气”关闭多个Closeable的方法
     *
     * @param closeables 多个Closeable对象
     */
    public static void closeQuietly(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (final Closeable closeable : closeables) {
            closeQuietly(closeable);
        }
    }

    /**
     * 解决API 15及以下的Cursor都没有实现Closeable接口，因此调用Closeable参数会出现转换异常的问题
     * java.lang.IncompatibleClassChangeError: interfaces not implemented,
     *
     * @param c Cursor对象
     */
    public static void closeQuietly(Cursor c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            // ignore
        }
    }

    /**
     * 解决API 18及以下的ZipFile都没有实现Closeable接口，因此调用Closeable参数会出现转换异常的问题
     * java.lang.IncompatibleClassChangeError: interfaces not implemented,
     *
     * @param c Cursor对象
     */
    public static void closeQuietly(ZipFile c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            // ignore
        }
    }
}
