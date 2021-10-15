package com.tesla.framework.common.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;


public class ResUtil {



    /**
     * 获取本地资源字符串
     * @param  resId
     */
    public static final String getString(Context context, int resId) {
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
     * 获取xml中定义的字符串数组
     * @param resId
     * @return
     */
    public static final String[] getStringArray(Context context, int resId) {
        if (context == null || context.getResources() == null){
            return  new String[]{};
        }
        String[] string = null;
        try {
            string =  context.getResources().getStringArray(resId);
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
    public static final String getString(Context context, String resStr) {
        if (context == null || context.getResources() == null){
            return  "";
        }
        String string = "";
        try {
            Resources resources = context.getResources();
            String packageName = context.getPackageName();
            int resId = resources.getIdentifier(resStr, "string", packageName);
            return getString(context, resId);
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
    public static final String getString(Context context, int resID, Object... obj){
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

    public static final String getString(Context context, String resID, Object[] obj){
        if (context == null || context.getResources() == null){
            return  "";
        }
        String string = "";
        try {
            string =  String.format(resID, obj);
        } catch (Exception e) {
        }
        return string;
    }


    /**
     * 获取本地Color
     *
     * @param  resId
     */
    public static final int getColor(Context context, int resId) {
        int color = 0x00000000;
        try {
            color = ContextCompat.getColor(context,resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }

    public static final ColorStateList getColorStateList(Context context, int resId) {
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
    public static final Drawable getDrawable(Context context, @DrawableRes int resId) {
        Drawable drawable = null;
        try {
            drawable = ContextCompat.getDrawable(context,resId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return drawable;
    }


    public static int getResourceId(Context context,String name,String defType){
        return context.getResources().getIdentifier(name,defType,context.getPackageName());
    }

    public static int getStringResourceId(Context context,String name){
        return getResourceId(context,name, "string");
    }

    public static int getArrayResourceId(Context context,String name){
        return getResourceId(context,name, "array");
    }


    public static String getResourceName(Context context, int id){
        if (context == null || context.getResources() == null){
            return  "";
        }
        return context.getResources().getResourceEntryName(id);
    }







}
