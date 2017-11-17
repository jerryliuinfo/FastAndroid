package com.tesla.framework.common.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.tesla.framework.common.util.log.NLog;

/**
 * Created by jerryliu on 2017/6/3.
 */

public class ManifestUtil {

    /**
     * 获取MetaData
     */
    public static String getMetaData(Context context, String name) {
        PackageManager packageManager = context.getPackageManager();
        ApplicationInfo applicationInfo;
        Object value = null;
        try {

            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null && applicationInfo.metaData != null) {
                value = applicationInfo.metaData.get(name);
            }

        } catch (Exception e) {
            NLog.printStackTrace(e);
            NLog.w("ContextUtils", "Could not read the name(%s) in the manifest file.", name);
            return null;
        }

        return value == null ? null : value.toString();
    }
}
