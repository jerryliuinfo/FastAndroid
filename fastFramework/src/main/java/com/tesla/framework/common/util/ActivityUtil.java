package com.tesla.framework.common.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import com.tesla.framework.common.util.log.NLog;

import java.util.List;

/**
 * Created by 01370340 on 2017/10/3.
 */

public class ActivityUtil {

    public static void startActivity(Activity context, String packageName) {
        try {
            Log.e("startActivity", packageName);
            // 获取目标应用安装包的Intent
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent == null) {
                startActivityByApplication(context, packageName);
                return;
            }
            context.startActivity(intent);
        } catch (Exception e) {
            // e.printStackTrace();
            NLog.printStackTrace(e);
        }
    }

    private static void startActivityByApplication(Context context, String packageNameStr) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageNameStr, 0);

            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            resolveIntent.setPackage(pi.packageName);

            List<ResolveInfo> apps = pm.queryIntentActivities(resolveIntent, 0);

            ResolveInfo ri = apps.iterator().next();
            if (ri != null) {
                String packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);

                ComponentName cn = new ComponentName(packageName, className);

                intent.setComponent(cn);
                context.startActivity(intent);
                return;
            }
        } catch (Exception e) {
        }
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        if (context == null || intent == null)
            return false;
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }
}
