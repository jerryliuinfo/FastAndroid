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

import android.text.TextUtils;

import java.io.File;

/**
 * 日志输出类，可控制调试与文件日志的控制
 * @author devilxie
 * @version 1.0
 */
public final class NLog
{
	private final static String	LOG_FILENAME	= "tcl_logcat.log";
	private static boolean		debug			= false;			// 是否记录日志
	private static Logger		logger			= null;
	private static final String LOGGING_PROPERTIES = "logging.properties";
	
	public static Logger getLogger()
	{
		return logger;
	}

	public static synchronized boolean init(String filePath) {

		return false;
	}

	public static synchronized void setDebug(boolean d, int level)
	{
		boolean old = debug;
		if (old == d)
			return;
		
		
		if (old) {
			trace(Logger.TRACE_REALTIME, null);
		}
		
		debug = d;
		
		if (d) {
			if (logger == null)
			{
				logger = Logger.getLogger(null);
			}
			
			logger.setLevel(level);			
		}
	}	

	public static boolean isDebug()
	{
		return debug;
	}

	public static synchronized boolean trace(int level, String path)
	{
		
		if (debug == false) 
			throw new IllegalStateException("you should enable log before modifing trace mode");
		
		if (logger == null)
		{
			logger = Logger.getLogger(null);
		}
		
		if (level == Logger.TRACE_ALL || level == Logger.TRACE_OFFLINE) {
			if (TextUtils.isEmpty(path))
				throw new IllegalArgumentException("com.wcc.core.path should not be null for offline and all mode");
			
			File dir = new File(path);
			if (!dir.exists() || !dir.isDirectory()) {
				boolean b = dir.mkdirs();
				if (!b)
					return false;
			}
			
			StringBuffer sb = new StringBuffer(path);
			sb.append(File.separator);
			sb.append(LOG_FILENAME);
			path = sb.toString();
		}
		
		
		return logger.trace(level, path);
	}
	
	private static String buildWholeMessage(String format, Object...args)
	{
		if (args == null || args.length == 0)
			return format;
		
		String msg = String.format(format, args);
		return msg;
	}

	public static void d(String tag, String format, Object...args)
	{
		if (debug)
		{
			logger.d(tag, buildWholeMessage(format, args));
		}
	}

	public static void i(String tag, String format, Object...args)
	{
		if (debug)
		{
			logger.i(tag, buildWholeMessage(format, args));
		}
	}

	public static void e(String tag, String format, Object...args)
	{
		if (debug)
		{
			logger.e(tag, buildWholeMessage(format, args));
		}
	}

	public static void e(String tag, Throwable e)
	{
		if (debug)
		{
			logger.e(tag, e);
		}
	}

	public static void v(String tag, String format, Object...args)
	{
		if (debug)
		{
			NLog.v(tag, buildWholeMessage(format, args));
		}
	}

	public static void w(String tag, String format, Object...args)
	{
		if (debug)
		{
			logger.w(tag, buildWholeMessage(format, args));
		}
	}

	public static void printStackTrace(Throwable e)
	{
		if (debug)
		{
			logger.e("TCLException", e);
		}
	}
}
