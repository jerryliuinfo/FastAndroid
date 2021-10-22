package com.tesla.framework.common.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.tesla.framework.applike.FApplication;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;


public class ResUtil {



    /**
     * 获取本地资源字符串
     * @param  resId
     */
    public static final String getString( int resId) {

        String string = "";
        try {
            string =  FApplication.getApplication().getResources().getString(resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }


    /**
     * 根据字符串名称获取字符串值,例如传"nav_posts" 获取到的值为主题帖子
     *  <string name="nav_posts">主题帖子</string>
     * @param  resStr
     */
    public static final String getString(String resStr) {
        Context context = FApplication.getApplication();
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
     * 获取xml中定义的字符串数组
     * @param resId
     * @return
     */
    public static final String[] getStringArray(int resId) {

        String[] string = null;
        try {
            string =  FApplication.getApplication().getResources().getStringArray(resId);
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
        String string = "";
        try {
            string =  FApplication.getApplication().getResources().getString(resID, obj);
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
        int color = 0x00000000;
        try {
            color = ContextCompat.getColor(FApplication.getApplication(),resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    public static final ColorStateList getColorStateList(int resId) {
        ColorStateList result = null;
        try {
            result = ContextCompat.getColorStateList(FApplication.getApplication(),resId);
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
    public static final Drawable getDrawable(@DrawableRes int resId) {
        Drawable drawable = null;
        try {
            drawable = ContextCompat.getDrawable(FApplication.getApplication(),resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }


    public static int getResourceIdByType(Context context, String name, String defType){
        return context.getResources().getIdentifier(name,defType,context.getPackageName());
    }

    public static int getStringResourceId(Context context,String name){
        return getResourceIdByType(context,name, "string");
    }

    public static int getArrayResourceId(Context context,String name){
        return getResourceIdByType(context,name, "array");
    }


    public static String getResourceName(Context context, int id){
        if (context == null || context.getResources() == null){
            return  "";
        }
        return context.getResources().getResourceEntryName(id);
    }







}
