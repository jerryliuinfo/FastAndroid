/*
 * Copyright (c) 1998-2012 TENCENT Inc. All Rights Reserved.
 * 
 * FileName: Logger.java
 * 
 * Description: 日志操作类文件
 * 
 * History:
 * 1.0	devilxie	2012-09-05	Create
 */
package com.tesla.framework.common.util.log;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志操作类，提供各种级别的日志输出，用于问题定位以及业务数据参考
 * @author devilxie
 * @version 1.0
 */
public class Logger
{
	/**
	 * 日志跟踪级别
	 * TRACE_REALTIME 表示实时跟踪，将只显示在logcat中
	 * TRACE_OFFLINE 表示离线跟踪，将只保存在特定日志文件中
	 * TRACE_ALL 表示都跟踪
	 */
	public static final int TRACE_REALTIME 	= 0x00000001;
	public static final int TRACE_OFFLINE 	= 0x00000010;
	public static final int TRACE_ALL		= 0x00000011;
	/**
	 * 日志优先级， 用于println，等同于Log.v，属于细节化的内容，一般用于详细跟踪函数执行情况以及调度情况时采用该级别日志
	 */
	public static final int				VERBOSE		= 2;

	/**
	 * 日志优先级， 用于println，等同于Log.d，属于调试的内容，一般用于调试跟踪日志。主要用于开发，产品发布阶段建议把该
	 * 部分日志删除
	 */
	public static final int				DEBUG		= 3;

	/**
	 * 日志优先级， 用于println，等同于Log.i，属于简单的提示性内容，可用于跟踪流程完成情况，以及辅助性查看内容
	 */
	public static final int				INFO		= 4;

	/**
	 * 日志优先级， 用于println，等同于Log.w，属于警告级别内容，在函数执行情况或者初始条件不满足预期时，可以采用该种日志
	 */
	public static final int				WARN		= 5;

	/**
	 *日志优先级， 用于println，等同于Log.e，属于错误级别内容，在函数执行发生错误或错误捕捉时，可以采用该种日志
	 */
	public static final int				ERROR		= 6;

	/**
	 * 日志优先级， 用于println，属于最高级别断言级别内容，只在单元测试使用断言时使用
	 */
	public static final int				ASSERT		= 7;


	/**
	 * 是否进行在线实时跟踪，如果为true，将不写入相关日志文件
	 */
	protected int					trace		= TRACE_REALTIME;
	/**
	 * 日志级别
	 */
	private int							level		= INFO;
	/**
	 * 日志串行记录锁对象，可用于控制日志记录的安全性
	 */
	private final Object				mLogLock	= new Object();
	/**
	 * 日志文件路径
	 */
	private String						logFileName	= null;

	private Logger(final String fileName, int level)
	{
		this.logFileName = fileName;
		this.level = level;
	}

	public static Logger getLogger(final String fileName)
	{
		return new Logger(fileName, INFO);
	}	

	protected boolean trace(int traceLevel, String logPath)
	{
		if (traceLevel <= 0 || traceLevel > TRACE_ALL) {
			throw new IllegalArgumentException(
					"param traceLevel invalid");
		}
		
		if ( (traceLevel & TRACE_OFFLINE)!= 0 && (logPath == null || logPath.length() == 0)) {
			throw new IllegalArgumentException(
					"offline trace level should with valid logPath");
		}
		
		closeLogStream();
		if ((traceLevel & TRACE_OFFLINE)!= 0) {
			this.logFileName = logPath;
		}
		
		this.trace = traceLevel;
		return openLogStream();
	}
	
	private void closeLogStream() {
		if ((trace & TRACE_OFFLINE)== 0 || this.logFileName == null || this.logFileName.length() == 0) {
			return;
		}
		
		synchronized (mLogLock) {
			if (logWriter != null)  {
				try {
					logWriter.close();
				} catch (IOException e) {
				}
				
				logWriter = null;
			}
		}
	}
	
	private boolean openLogStream()
	{
		if ((trace & TRACE_OFFLINE)== 0 || this.logFileName == null || this.logFileName.length() == 0) {
			return true;
		}
		
		synchronized (mLogLock) {
			OutputStreamWriter writer = null;
			File file = new File(logFileName);
			try {
				// not exist
				if (!file.exists())	{
					file.createNewFile();
				}
				
				writer = new FileWriter(file, true);
				logWriter = writer;
				failCount = 0;
				return true;
			} catch (IOException e) {
				return false;
			}
		}
		
		
	}

	public int getTraceLevel()
	{
		return trace;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public int v(String tag, String msg)
	{
		if (VERBOSE < level)
			return 0;
		int ret = 0;
		if ((trace & TRACE_REALTIME) > 0)
			ret = Log.v(tag, msg);
		
		if ((trace & TRACE_OFFLINE) > 0)
			ret = println(VERBOSE, tag, msg);
		
		return ret;
	}

	public int v(String tag, Throwable tr)
	{
		if (VERBOSE < level)
			return 0;
		return v(tag, getStackTraceString(tr));
	}

	public int d(String tag, String msg)
	{
		if (TextUtils.isEmpty(msg)){
			return 0;
		}
		if (DEBUG < level)
			return 0;
		
		int ret = 0;
		if ((trace & TRACE_REALTIME) > 0)
			ret = Log.d(tag, msg);
		
		if ((trace & TRACE_OFFLINE) > 0)
			ret = println(DEBUG, tag, msg);
		
		return ret;
	}

	public int d(String tag, Throwable tr)
	{
		if (DEBUG < level)
			return 0;
		return d(tag, getStackTraceString(tr));
	}

	public int w(String tag, String msg)
	{
		if (WARN < level)
			return 0;
		int ret = 0;
		if ((trace & TRACE_REALTIME) > 0)
			ret = Log.w(tag, msg);
		
		if ((trace & TRACE_OFFLINE) > 0)
			ret = println(WARN, tag, msg);
		
		return ret;
	}

	public int w(String tag, Throwable tr)
	{
		if (WARN < level)
			return 0;
		return w(tag, getStackTraceString(tr));
	}

	public int i(String tag, String msg)
	{
		if (INFO < level)
			return 0;
		
		int ret = 0;
		if ((trace & TRACE_REALTIME) > 0)
			ret = Log.i(tag, msg);
		
		if ((trace & TRACE_OFFLINE) > 0)
			ret = println(INFO, tag, msg);
		
		return ret;
	}

	public int i(String tag, Throwable tr)
	{
		if (INFO < level)
			return 0;
		return i(tag, getStackTraceString(tr));
	}

	public int e(String tag, String msg)
	{
		
		if (ERROR < level)
			return 0;
		int ret = 0;
		if ((trace & TRACE_REALTIME) > 0)
			ret = Log.e(tag, msg);
		
		if ((trace & TRACE_OFFLINE) > 0)
			ret = println(ERROR, tag, msg);
		
		return ret;
	}

	public int e(String tag, Throwable tr)
	{
		if (ERROR < level)
			return 0;
		return e(tag, getStackTraceString(tr));
	}

	public String getStackTraceString(Throwable tr)
	{
		if (tr == null)
		{
			return "";
		}
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		tr.printStackTrace(pw);
		return sw.toString();
	}

	OutputStreamWriter logWriter = null;
	int failCount = 0;
	private int println(int priority, String tag, String msg)
	{
		if (priority < level || failCount >= 5 || logWriter == null)
		{
			return 0;
		}

		String[] ps = { "", "", "V", "D", "I", "W", "E", "A" };
		SimpleDateFormat df = new SimpleDateFormat("[MM-dd HH:mm:ss.SSS]");
		String time = df.format(new Date());
		StringBuilder sb = new StringBuilder();
		sb.append(time);
		sb.append("\t");
		sb.append(ps[priority]);
		sb.append("/");
		sb.append(tag);
		/*int pid = java.lang.Process.myPid();
		sb.append("(");
		sb.append(pid);
		sb.append("):");*/
		sb.append(msg);
		sb.append("\n");
		

		synchronized (mLogLock)
		{
			
			final OutputStreamWriter writer = logWriter;
			try
			{				
				if (writer != null ) {
					writer.write(sb.toString());
					writer.flush();
				}
				
			}
			catch (FileNotFoundException e)
			{
				failCount ++;
				return -1;
			}
			catch (IOException e)
			{
				failCount ++;
				return -1;
			} finally {
				if (failCount >= 5) {
					closeLogStream();
				}
			}
			
		}

		return 0;
	}
}
