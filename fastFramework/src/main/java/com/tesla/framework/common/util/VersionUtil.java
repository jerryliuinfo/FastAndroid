package com.tesla.framework.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

/**
 * Created by 01370340 on 2017/10/3.
 */

public class VersionUtil {
    private static final String SP_VERSION_INFO = "version_info";
    private static int NEW_VERSION_CODE = -1;
    private static String NEW_VERSION_NAME = "";


    private static final String LAST_VERSION_NAME = "LAST_VERSION_NAME";
    private static final String LAST_VERSION_CODE = "LAST_VERSION_CODE";

    public static int getVersionCode(Context context) {
        if (NEW_VERSION_CODE == -1){
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                NEW_VERSION_CODE = packageInfo.versionCode;
            } catch (Exception e) {
            }
        }
        return NEW_VERSION_CODE;
    }
    public static String getVersionName(Context context) {
        try {
            if (TextUtils.isEmpty(NEW_VERSION_NAME)){
                PackageInfo packageInfo = getPackageInfo(context);
                if (packageInfo != null){
                    NEW_VERSION_NAME = packageInfo.versionName;
                }
            }
        } catch (Exception e) {
        }
        return NEW_VERSION_NAME;
    }
    public static String getVersionNameAndFormat(Context context,String format) {
        try {
            if (TextUtils.isEmpty(NEW_VERSION_NAME)){
                PackageInfo packageInfo = getPackageInfo(context);
                if (packageInfo != null){
                    NEW_VERSION_NAME = packageInfo.versionName;
                }
            }
        } catch (Exception e) {
        }
        return NEW_VERSION_NAME +format;
    }


    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (Exception ex) {
        }

        return packageInfo;
    }


    public static boolean isNewVersion(Context context) {
        PackageInfo packageInfo = getPackageInfo(context);
        if (null != packageInfo) {
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;

            SharedPreferences sp = context.getSharedPreferences(SP_VERSION_INFO, Context.MODE_PRIVATE);
            if (!versionName.equals(sp.getString(LAST_VERSION_NAME, null)) || versionCode != sp.getInt(LAST_VERSION_CODE, -1)) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }

    public static void updateVersion(Context context) {
        //if (!android.text.TextUtils.isEmpty(NEW_VERSION_NAME) && NEW_VERSION_CODE != 0) {
            SharedPreferences sp = context.getSharedPreferences(SP_VERSION_INFO, Context.MODE_PRIVATE);
            sp.edit().putString(LAST_VERSION_NAME, getVersionName(context)).putInt(LAST_VERSION_CODE, getVersionCode(context)).apply();
        //}
    }
}
