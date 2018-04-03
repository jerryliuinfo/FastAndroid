package com.tesla.framework.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by 01370340 on 2017/10/3.
 */

public class VersionUtil {


    private static int sAppVersionCode = -1;
    private static String sAppVersionName = "";

    public static int getVersionCode(Context context) {
        if (sAppVersionCode == -1){
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                sAppVersionCode = packageInfo.versionCode;
            } catch (Exception e) {
            }
        }
        return sAppVersionCode;
    }
    public static String getVersionName(Context context) {
        if (TextUtils.isEmpty(sAppVersionName)){
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                sAppVersionName =  packageInfo.versionName;
            } catch (Exception e) {
            }
        }

        return sAppVersionName;
    }

    public static String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
        }
        return "";
    }
}
