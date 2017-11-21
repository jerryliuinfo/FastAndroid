package com.tesla.framework.common.util.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashSet;
import java.util.Set;

/**
 * 在Application中一定要先配置
 * 
 * @author JerryLiu
 *
 */
public class ActivityHelper {
	
	public static final String CONFIG = "org.aisen.android.activityhelp_key";
	
	private ActivityHelper() {
	}

	public static SharedPreferences getSharedPreferences(Context context, String fileName){
		return context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
	}


	/**
	 * 获取string，默认值为""
	 * 
	 * @param key
	 * @return
	 */
	public static String getStringData(Context context, String key) {
		return getStringData(context,key,"");
	}

	/**
	 * 获取string
	 * 
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getStringData(Context context, String key, String defValue) {
		SharedPreferences sp = getSharedPreferences(context,CONFIG);
		return sp.getString(key, defValue);
	}

	public static Set<String> getSetShareData(Context context, String key) {
		SharedPreferences sp = getSharedPreferences(context,CONFIG);
		return sp.getStringSet(key, new HashSet<String>());
	}


	public static int getIntShareData(Context context, String key) {
		return getIntShareData(context,key,-1);
	}

	public static int getIntShareData(Context context, String key, int defValue) {
		SharedPreferences sp = getSharedPreferences(context,CONFIG);
		return sp.getInt(key, defValue);
	}
	public static int getLongShareData(Context context, String key) {
		return getLongShareData(context,key,-1);
	}

	public static int getLongShareData(Context context, String key, int defValue) {
		SharedPreferences sp = getSharedPreferences(context,CONFIG);
		return sp.getInt(key, defValue);
	}



	public static boolean getBooleanShareData(Context context, String key) {
		return getBooleanShareData(context,key,false);
	}

	public static boolean getBooleanShareData(Context context, String key, boolean defValue) {
		SharedPreferences sp = getSharedPreferences(context,CONFIG);
		return sp.getBoolean(key, defValue);
	}

	public static void putStringShareData(Context context, String key, String value) {
		Editor et = getSharedPreferences(context,CONFIG).edit();
		et.putString(key, value);
		et.commit();
	}

	public static void putIntShareData(Context context, String key, int value) {
		Editor et = getSharedPreferences(context,CONFIG).edit();
		et.putInt(key, value);
		et.commit();
	}
	public static void putLongShareData(Context context, String key, int value) {
		Editor et = getSharedPreferences(context,CONFIG).edit();
		et.putLong(key, value);
		et.commit();
	}

	public static void putBooleanShareData(Context context, String key, boolean value) {
		Editor et = getSharedPreferences(context,CONFIG).edit();
		et.putBoolean(key, value);
		et.commit();
	}

	public static void putSetShareData(Context context, String key, Set<String> value) {
		Editor et = getSharedPreferences(context,CONFIG).edit();
		et.putStringSet(key, value);
		et.commit();
	}



}
