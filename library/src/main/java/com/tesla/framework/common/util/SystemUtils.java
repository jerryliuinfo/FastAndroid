package com.tesla.framework.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.SystemClock;

import java.io.File;

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






}
