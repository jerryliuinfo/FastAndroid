package com.tesla.framework.common.util;

/**
 * Created by 01370340 on 2018/4/11.
 */

public class Utils {

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
