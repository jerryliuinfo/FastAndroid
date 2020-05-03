package com.tesla.framework.common.util;

/**
 * Created by 01370340 on 2018/5/2.
 */

public class CheckUtil {



    public static <T> T checkNotNull(T reference, String format, Object... args) {
        if (reference == null) {
            throw new NullPointerException(String.format(format, args));
        } else {
            return reference;
        }
    }
}
