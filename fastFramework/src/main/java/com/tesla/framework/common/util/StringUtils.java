package com.tesla.framework.common.util;

/**
 * Created by 01370340 on 2018/3/7.
 */

public class StringUtils {

    private StringUtils(){

    }

    public static boolean isNotEmtpy(String str) {
        return (str != null && !"".equalsIgnoreCase(str.trim()) && !"null".equalsIgnoreCase(str));
    }

    public static boolean isEmpty(String str){
        return !isNotEmtpy(str);
    }
}
