package com.tesla.framework.common.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.tesla.framework.FrameworkApplication;


/**
 * @author liangni
 * @Description:资源类Util
 * @date 2016/4/11 15:36
 * @copyright TCL-MIG
 */
public class ResUtil {

    /**
     * 获取本地资源字符串
     * @param  resId
     */
    public static final String getString(int resId) {
        Context context = FrameworkApplication.getContext();
        if (context == null || context.getResources() == null){
            return  "";
        }
        String string = "";
        try {
            string =  context.getResources().getString(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 获取本地资源字符串
     * @param  resStr
     */
    public static final String getString(String resStr) {
        Context context = FrameworkApplication.getContext();
        if (context == null || context.getResources() == null){
            return  "";
        }
        String string = "";
        try {
            Resources resources = context.getResources();
            String packageName = context.getPackageName();
            int resId = resources.getIdentifier(resStr, "string", packageName);
            return getString(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    /**
     * 获取本地字符串
     * @param resID
     * @param obj
     * @return
     */
    public static final String getString(int resID, Object... obj){
        Context context = FrameworkApplication.getContext();
        if (context == null || context.getResources() == null){
            return  "";
        }
        String string = "";
        try {
            string =  context.getResources().getString(resID, obj);
        } catch (Exception e) {
        }
        return string;
    }

    /**
     * 获取本地Color
     *
     * @param  resId
     */
    public static final int getColor(int resId) {
        Context context = FrameworkApplication.getContext();
        int color = 0x00000000;
        try {
            color = ContextCompat.getColor(context,resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    public static final ColorStateList getColorStateList(int resId) {
        Context context = FrameworkApplication.getContext();
        ColorStateList result = null;
        try {
            result = ContextCompat.getColorStateList(context,resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取本地Color
     *
     * @param  resId
     */
    public static final Drawable getDrawable(int resId) {
        Context context = FrameworkApplication.getContext();
        Drawable drawable = null;
        try {
            drawable = ContextCompat.getDrawable(context,resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }







}
