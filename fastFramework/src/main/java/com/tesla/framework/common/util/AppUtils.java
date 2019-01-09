/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tesla.framework.common.util;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.List;

public class AppUtils {


    public static boolean isAppInBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return true;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND;
            }
        }
        return false;
    }

    public static String getAppName(Context context) {
        PackageManager packageManager = null;
        ApplicationInfo applicationInfo = null;
        if (!(context instanceof Application))
            context = context.getApplicationContext();
        try {
            packageManager = context.getPackageManager();
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        CharSequence charSequence = packageManager.getApplicationLabel(applicationInfo);
        return charSequence == null ? "" : (String) charSequence;
    }

    public static boolean isMainProcess(Context context) {
        try {
            ActivityManager am = ((ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE));
            List<ActivityManager.RunningAppProcessInfo> processInfo = am.getRunningAppProcesses();
            String mainProcessName = context.getPackageName();
            int myPid = android.os.Process.myPid();
            for (ActivityManager.RunningAppProcessInfo info : processInfo) {
                if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);
    }


}
