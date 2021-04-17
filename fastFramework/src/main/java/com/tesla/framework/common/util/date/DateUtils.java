package com.tesla.framework.common.util.date;

import com.blankj.utilcode.util.AppUtils;
import com.tesla.framework.common.util.log.FastLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

public class DateUtils {

	// yyyy-MM-dd hh:mm:ss 12小时制
	// yyyy-MM-dd HH:mm:ss 24小时制

	public static final String TYPE_01 = "yyyy-MM-dd HH:mm:ss";

	public static final String TYPE_02 = "yyyy-MM-dd";

	public static final String TYPE_03 = "HH:mm:ss";


	public static String formatDate(long time, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return new SimpleDateFormat(format).format(cal.getTime());
	}

	public static String formatDate(String longStr, String format) {
		try {
			return formatDate(Long.parseLong(longStr), format);
		} catch (Exception e) {
		}
		return "";
	}
	
	public static long formatStr(String timeStr, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		try {
			return sdf.parse(timeStr).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return 0;
	}



	public static final SimpleDateFormat SIMPLE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	public static final SimpleDateFormat SIMPLE_FORMAT_24 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat SIMPLE_FORMAT_DATE_MINUTE = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public static String simpleFormatLong(long mis) {
		return SIMPLE_FORMAT.format(new Date(mis));
	}

	public static final SimpleDateFormat ONLY_DATE = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat REPORT_DATE = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat ONLY_HOURANDMIN = new SimpleDateFormat("HH:mm");
	private static TimeZone timeZone = null;

	public static String simpleFormat(long ms) {
		return ONLY_DATE.format(new Date(ms));
	}

	public static String simpleFormatHour(long ms) {
		return ONLY_HOURANDMIN.format(new Date(ms));
	}

	public static String simpleFormatMinute(long ms) {
		return SIMPLE_FORMAT_DATE_MINUTE.format(new Date(ms));
	}

	private static final long MILLIS_IN_DAY = 1000L * 60 * 60 * 24;

	public static String simpleFormatOfCN() {
		return getFormatedDateString();
	}

	private static TimeZone getBeiJingTimeZone() {
		if (timeZone == null) {
			int newTime = 8 * 60 * 60 * 1000;
			String[] ids = TimeZone.getAvailableIDs(newTime);
			if (ids.length == 0) {
				timeZone = TimeZone.getDefault();
			} else {
				timeZone = new SimpleTimeZone(newTime, ids[0]);
			}
		}
		return timeZone;
	}

	private static String getFormatedDateString() {
		REPORT_DATE.setTimeZone(getBeiJingTimeZone());
		return REPORT_DATE.format(new Date());
	}

	public static Date dbParse(String str) {
		try {
			return ONLY_DATE.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Date();
	}

	public static String dbFormat(Date date) {
		return ONLY_DATE.format(date);
	}

	public static boolean isSameDay(long millis1, long millis2) {
		return toDay(millis1) == toDay(millis2);
	}

	private static int toDay(long millis) {
		return (int) ((millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY);
	}

	/**
	 * 获取某一日期到今天相隔的天数
	 */
	public static int getDaysToNow(Date date1) {
		return getDaysBetweenTwoMillis(date1.getTime(), System.currentTimeMillis());
	}

	public static int getDaysBetweenTwoMillis(long millis1, long millis2) {
		int day1 = toDay(millis1);
		int day2 = toDay(millis2);
		return day2 - day1;
	}

	/**
	 * 时间戳获取日
	 */
	public static String getDay(long time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd", Locale.CHINA);
		return simpleDateFormat.format(new Date(time * 1000));
	}

	/**
	 * 时间戳获取月
	 */
	public static String getMonth(long time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM", Locale.CHINA);
		return simpleDateFormat.format(new Date(time * 1000));
	}

	/**
	 * 时间戳获取年
	 */
	public static String getYear(long time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
		return simpleDateFormat.format(new Date(time * 1000));
	}

	/**
	 * 获取详细日期信息
	 */
	public static String getDateTime(long time) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.CHINA);
		return simpleDateFormat.format(new Date(time));
	}

	/**
	 * 获取以北京时间的小时,24小时制
	 */
	public static int getDataHour() {
		Calendar calendar = Calendar.getInstance(getBeiJingTimeZone());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取客户端对应日期的时间戳
	 */
	public static long getDate24Long(String value) {
		long vl = 0;
		try {
			vl = SIMPLE_FORMAT_24.parse(value).getTime();
		} catch (Exception e) {
			if (AppUtils.isAppDebug()) {
				FastLog.printStackTrace(e);
			}
		}
		return vl;
	}

	public static boolean isToday(long tm) {
		return toDay(tm) == toDay(System.currentTimeMillis());
	}

	public static boolean isYesterday(long tm) {
		return toDay(System.currentTimeMillis()) - toDay(tm) == 1;
	}

}
