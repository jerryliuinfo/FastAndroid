package com.tesla.framework.common.util;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * Created by jerryliu on 2017/6/10.
 */

public class PhoneUtil {
    public static String getIMSI(Context context) {
        String result = "";
        try{
            TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            result =  telManager.getSubscriberId();

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return result;
    }


    /**
     * 获取手机机型信息
     * 如果pixi android5手机获取model的api为反射ro.build.product方式
     * @return
     */
    public static String getPhoneModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获得手机品牌
     * @return
     */
    public static String getPhoneBrand(){
        return Build.BRAND;
    }

    public static String getOsVersionName(){
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取手机的Android_ID
     * @return
     */
    public static String getAndroidId(Context context){
        return Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }


}
