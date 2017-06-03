package com.tesla.framework.common.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.tesla.framework.FrameworkApplication;

/**
 * @author Jerry
 * @Description:
 * @date 2016/8/4 10:47
 * @copyright TCL-MIG
 */

public abstract class BaseSharedPreferenceConfigManager {
    private SharedPreferences sharedPreference;
    protected   SharedPreferences getSharedPreference(){
        if (sharedPreference == null){
            sharedPreference = FrameworkApplication.getContext().getSharedPreferences(configSPFileName(), Context.MODE_PRIVATE);
        }
        return sharedPreference;
    }

    /**
     * 配置SharedPreference文件名字
     * @return
     */
    public abstract String configSPFileName();

    public long getLongValue(String key, long defValue) {
        return getSharedPreference().getLong(key, defValue);
    }

    public void setLongValue(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putLong(key, value);
        applyToEditor(editor);
    }



    public int getIntValue(String key, int defValue) {
        return getSharedPreference().getInt(key, defValue);
    }

    public void setIntValue(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putInt(key, value);
        applyToEditor(editor);
    }

    public String getStringValue(String key, String defValue) {
        return getSharedPreference().getString(key, defValue);
    }

    public void setStringValue(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putString(key, value);
        applyToEditor(editor);
    }

    public void setBooleanValue(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreference().edit();
        editor.putBoolean(key, value);
        applyToEditor(editor);
    }

    public boolean getBooleanValue(String key, boolean defValue) {
        return getSharedPreference().getBoolean(key, defValue);
    }

    public static void applyToEditor(SharedPreferences.Editor editor) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            editor.apply();
        } else {
            editor.commit();
        }
    }
}
