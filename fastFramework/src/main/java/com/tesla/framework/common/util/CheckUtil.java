package com.tesla.framework.common.util;

import android.support.annotation.Nullable;

/**
 * Created by 01370340 on 2018/5/2.
 */

public class CheckUtil {


    public static <T> T checkNotNull(T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }
}
