package com.tesla.framework.common.util.format;

import android.text.TextUtils;

import java.text.DecimalFormat;

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


    /**
     * 转换文件大小
     *
     * @param fileLen 单位B
     * @return
     */
    public static String formatFileSizeToString(long fileLen) {
        DecimalFormat df = new DecimalFormat("0.00");
        String fileSizeString = "";
        if (fileLen < 1024) {
            fileSizeString = df.format((double) fileLen) + "B";
        } else if (fileLen < 1048576) {
            fileSizeString = df.format((double) fileLen / 1024) + "K";
        } else if (fileLen < 1073741824) {
            fileSizeString = df.format((double) fileLen / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileLen / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
