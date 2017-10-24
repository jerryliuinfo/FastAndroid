package com.tesla.framework.common.util;

import android.text.TextUtils;

/**
 * Created by 01370340 on 2017/10/18.
 */

public class FormatUtil {

    public static String formatString(String format, Object... args){
        try {
            if (args == null || args.length == 0)
                return format;
            if (TextUtils.isEmpty(format)){
                return "";
            }
            return String.format(format,args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
