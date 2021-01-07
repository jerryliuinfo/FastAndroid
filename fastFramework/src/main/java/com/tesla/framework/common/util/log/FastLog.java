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

/**
 * 日志输出类，可控制调试与文件日志的控制
 * @author devilxie
 * @version 1.0
 */
public final class FastLog
{
	public static final String TAG = "NLog";
	private final static String	LOG_FILENAME	= "tcl_logcat.log";
	private static boolean		debug			= false;			// 是否记录日志
	private static Logger		logger			= null;


	public static class LogConfig{
		public boolean openLog;
	}

	public interface IFastLogDelegate {
		void setLogConfig(LogConfig config);
		boolean isDebug();

		void e(final String tag, final String msg, final Object ... obj);
		void w(final String tag, final String msg, final Object ... obj);
		void i(final String tag, final String msg, final Object ... obj);
		void d(final String tag, final String msg, final Object ... obj);
		void v(final String tag, final String msg, final Object ... obj);
		void printStackTrace(String tag, Throwable e);
	}


	private static IFastLogDelegate sDelegete;

	public static void setLogDelegate(IFastLogDelegate iLogDelegate) {
		FastLog.sDelegete = iLogDelegate;
	}


	public static void e(final String tag, final String msg, final Object ... obj) {
		if (sDelegete != null) {
			sDelegete.e(tag, msg, obj);
		}
	}

	public static void w(final String tag, final String msg, final Object ... obj) {
		if (sDelegete != null) {
			sDelegete.w(tag, msg, obj);
		}
	}

	public static void i(final String tag, final String msg, final Object ... obj) {
		if (sDelegete != null) {
			sDelegete.i(tag, msg, obj);
		}
	}
	public static void v(final String tag, final String msg, final Object ... obj) {
		if (sDelegete != null) {
			sDelegete.v(tag, msg, obj);
		}
	}

	public static void d(final String tag, final String msg, final Object ... obj) {
		if (sDelegete != null) {
			sDelegete.d(tag, msg, obj);
		}
	}

	public static void printStackTrace(final String tag, Throwable throwable) {
		if (sDelegete != null) {
			sDelegete.printStackTrace(tag, throwable);
		}
	}

	public static void printStackTrace(Throwable throwable) {
		if (sDelegete != null) {
			sDelegete.printStackTrace("Log", throwable);
		}
	}

	public static boolean isDebug(){
		if (sDelegete != null){
			return sDelegete.isDebug();
		}
		return false;
	}
}
