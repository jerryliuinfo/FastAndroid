/*
 * Copyright (c) 1998-2012 TENCENT Inc. All Rights Reserved.
 * 
 * FileName: QTLog.java
 * 
 * Description: 日志输出工具类文件
 * 
 * History:
 * 1.0	devilxie	2012-09-05	Create
 */
package com.tesla.framework.common.util.log;

import android.util.Log;

/**
 * 日志输出类，可控制调试与文件日志的控制
 * @author devilxie
 * @version 1.0
 */
public final class FastLog
{
	public static final String TAG = "FastLog";


	public interface ILogDelegate {

		void w(final String tag, final String msg, final Object ... obj);
		void i(final String tag, final String msg, final Object ... obj);
		void d(final String tag, final String msg, final Object ... obj);
		void v(final String tag, final String msg, final Object ... obj);
		void printStackTrace(String tag, Throwable e);
	}

	public interface ILogConfig{
		boolean openLog();
	}

	public static class DefLogConfig implements ILogConfig{

		@Override
		public boolean openLog() {
			return true;
		}
	}

	public static class DefLog implements ILogDelegate{

		@Override
		public void w(String tag, String msg, Object... obj) {
			Log.w(TAG, String.format(msg,obj));
		}

		@Override
		public void i(String tag, String msg, Object... obj) {
			Log.i(TAG, String.format(msg,obj));
		}

		@Override
		public void d(String tag, String msg, Object... obj) {
			Log.d(TAG, String.format(msg,obj));
		}

		@Override
		public void v(String tag, String msg, Object... obj) {
			Log.v(TAG, String.format(msg,obj));
		}

		@Override
		public void printStackTrace(String tag, Throwable e) {
			e.printStackTrace();
		}
	}


	private static ILogDelegate sDelegate = new DefLog();
	private static ILogConfig sLogConfig = new DefLogConfig();

	/**
	 *  注入 log 实现类
	 * @param iLogDelegate
	 */
	public static void setLogDelegate(ILogDelegate iLogDelegate) {
		if (iLogDelegate != null){
			sDelegate = iLogDelegate;
		}
	}

	public static void setLogConfig(ILogConfig logConfig) {
		if (logConfig != null){
			sLogConfig = logConfig;
		}
	}



	public static void w(final String tag, final String msg, final Object ... obj) {
		if (sDelegate != null) {
			sDelegate.w(tag, msg, obj);
		}
	}

	public static void i(final String tag, final String msg, final Object ... obj) {
		if (sDelegate != null) {
			sDelegate.i(tag, msg, obj);
		}
	}
	public static void v(final String tag, final String msg, final Object ... obj) {
		if (sDelegate != null) {
			sDelegate.v(tag, msg, obj);
		}
	}

	public static void d(final String tag, final String msg, final Object ... obj) {
		if (sDelegate != null) {
			sDelegate.d(tag, msg, obj);
		}
	}

	public static void printStackTrace(final String tag, Throwable throwable) {
		if (sDelegate != null) {
			sDelegate.printStackTrace(tag, throwable);
		}
	}

	public static void printStackTrace(Throwable throwable) {
		if (sDelegate != null) {
			sDelegate.printStackTrace("Log", throwable);
		}
	}

	public static boolean isDebug(){
		if (sLogConfig != null){
			return sLogConfig.openLog();
		}
		return false;
	}


}
