package com.tesla.framework.common.util.sdcard;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;

import java.io.File;

public class SdcardUtils {
	public static String sdCardPath;


	
	public static boolean hasSDCard() {
		boolean mHasSDcard;
		if (Environment.MEDIA_MOUNTED.endsWith(Environment.getExternalStorageState())) {
			mHasSDcard = true;
		} else {
			mHasSDcard = false;
		}

		return mHasSDcard;
	}

	@SuppressLint("SdCardPath")
	public static String getSdcardPath() {
		if (!TextUtils.isEmpty(sdCardPath)){
			return sdCardPath;
		}

		if (hasSDCard()){
			sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
			return sdCardPath;
		}
		return "/sdcard/";
	}

	private static boolean sdcardCanWrite() {
		return Environment.getExternalStorageDirectory().canWrite();
	}

	public static boolean hasSdcardAndCanWrite() {
		return hasSDCard() && sdcardCanWrite();
	}

	/**
	 * 获取SDCARD的可用大小,单位字节
	 * 
	 * @return
	 */
	public long getSdcardtAvailableStore() {

		if (hasSdcardAndCanWrite()) {
			String path = getSdcardPath();
			if (path != null) {
				StatFs statFs = new StatFs(path);

				@SuppressWarnings("deprecation")
				long blocSize = statFs.getBlockSize();

				@SuppressWarnings("deprecation")
				long availaBlock = statFs.getAvailableBlocks();

				return availaBlock * blocSize;
			}
		}

		return 0;
	}



	/**
	 * 获取内置SD卡空间大小
	 * @return
	 */
	public static long getTotalInternalStorageSize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}




}
