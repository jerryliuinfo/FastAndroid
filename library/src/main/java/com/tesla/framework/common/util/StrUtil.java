package com.tesla.framework.common.util;

/**
 * Created by 01370340 on 2017/10/17.
 */

public class StrUtil {

    private static final char[] HEX_DIGITS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static final String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(HEX_DIGITS[(b & 0xf0) >> 4]);
            sb.append(HEX_DIGITS[b & 0x0f]);
        }
        return sb.toString();
    }

    public static boolean isNull(String str){
        if (str == null || str.trim().length() == 0 || str.equalsIgnoreCase("null") ){
            return true;
        }
        return false;
    }
    public static boolean isNotNull(String str){
        return !isNull(str);
    }

    public static int toInt(String str){
        return toInt(str,-1);
    }

    public static int toInt(String str,int def){
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return def;
    }

    public static long toLong(String str){
        return toLong(str,-1);
    }

    public static long toLong(String str,long def){
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return def;
    }



    public static float toFloat(String str){
        return toFloat(str,-1f);
    }

    public static float toFloat(String str,float def){
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return def;
    }

}
