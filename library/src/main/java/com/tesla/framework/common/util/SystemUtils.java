package com.tesla.framework.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.Settings;

import java.io.File;
import java.io.FileInputStream;

@SuppressLint("SdCardPath") public class SystemUtils {

	/**
	 * 获取 开机时间
	 */
	public static String getBootTimeString() {
		long ut = SystemClock.elapsedRealtime() / 1000;
		int h = (int) ((ut / 3600));
		int m = (int) ((ut / 60) % 60);
		return h + ":" + m;
	}

	public static void scanPhoto(Context context, File file) {
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		context.sendBroadcast(intent);
	}



	/**
	 * 获得当前屏幕亮度值
	 *
	 * @param mContext
	 * @return 0~100
	 */
	public static float getScreenBrightness(Context mContext) {
		int screenBrightness = 255;
		try {
			screenBrightness = Settings.System.getInt(mContext.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return screenBrightness / 255.0F * 100;
	}


	/**
	 * 返回当前的进程名
	 *
	 * @return
	 */
	public static String getCurrentProcessName() {
		FileInputStream in = null;
		try {
			String fn = "/proc/self/cmdline";
			in = new FileInputStream(fn);
			byte[] buffer = new byte[256];
			int len = 0;
			int b;
			while ((b = in.read()) > 0 && len < buffer.length) {
				buffer[len++] = (byte) b;
			}
			if (len > 0) {
				String s = new String(buffer, 0, len, "UTF-8");
				return s;
			}
		} catch (Throwable e) {
		} finally {
			CloseableUtils.closeQuietly(in);
		}
		return null;
	}



}
