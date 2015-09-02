/**
 * 
 */
package com.bignerdranch.android.criminalintent.util;

import android.annotation.TargetApi;
import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期工具类
 * @author keily
 *
 */
public class DateUtils {

	private static Calendar CALENDAR = Calendar.getInstance(TimeZone.getDefault(), Locale.CHINA);
	public static String PATTERN_HOUR_MINUTE = "HH时mm分";
	private DateUtils(){}
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("static-access")
	public static String formateDate2WeekMmDDYYYY(Date date){
		StringBuilder pattern = new StringBuilder();
		CALENDAR.setTime(date);
		String week = CALENDAR.getDisplayName(CALENDAR.DAY_OF_WEEK, CALENDAR.LONG, Locale.CHINA);
		pattern.append(week).append(",");
		String month = CALENDAR.getDisplayName(CALENDAR.MONTH, CALENDAR.LONG, Locale.CHINA);
		pattern.append(month).append(" ");
		pattern.append(CALENDAR.get(CALENDAR.DAY_OF_MONTH)).append(",");
		pattern.append(CALENDAR.get(CALENDAR.YEAR));
		
		return pattern.toString();
	}
	
	/**
	 *格式化日期对象
	 * @param date
	 * @param partten
	 * @return
	 */
	public static String formateDate2HourMinute(Date date,String partten){
		CALENDAR.setTime(date);
		SimpleDateFormat formate = new SimpleDateFormat(partten,Locale.CHINA);
		return formate.format(date);
	}
	public static void main(String[] args) {
		formateDate2HourMinute(new Date(), "hh时mm分");
	}
	/**
	 * 根据日期 返回year值
	 * @param date
	 * @return
	 */
	public static int getYearByDate(Date date){
		CALENDAR.setTime(date);
		return CALENDAR.get(Calendar.YEAR);
	}
	
	/**
	 * 根据日期 返回moth值
	 * @param date
	 * @return
	 */
	public static int getMonthByDate(Date date){
		CALENDAR.setTime(date);
		return CALENDAR.get(Calendar.MONTH);
	}
	
	/**
	 * 根据日期 返回day值
	 * @param date
	 * @return
	 */
	public static int getDayByDate(Date date){
		CALENDAR.setTime(date);
		return CALENDAR.get(Calendar.DATE);
	}
	
	/**
	 * 根据日期 返回hour值 12小时制
	 * @param date
	 * @return
	 */
	public static int getHourByDate(Date date){
		CALENDAR.setTime(date);
		return CALENDAR.get(Calendar.HOUR);
	}
	
	/**
	 * 根据日期 返回hour值 24小时制
	 * @param date
	 * @return
	 */
	public static int getHour24ByDate(Date date){
		CALENDAR.setTime(date);
		return CALENDAR.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 根据日期返回 minute 值
	 * @param date
	 * @return
	 */
	public static int getMinuteByDate(Date date){
		CALENDAR.setTime(date);
		return CALENDAR.get(Calendar.MINUTE);
	}
	
	/**
	 * 给date 设置hour24小时制 minute属性，并返回date对象
	 * @param date
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static Date getDateByHourAndMinute(Date date,int hour,int minute){
		CALENDAR.setTime(date);
		CALENDAR.set(Calendar.HOUR_OF_DAY, hour);
		CALENDAR.set(Calendar.MINUTE, minute);
		return CALENDAR.getTime();
	}
	
	public static Date getDateByYearMonthDay(Date date,int year,int month,int day){
		CALENDAR.setTime(date);
		CALENDAR.set(Calendar.YEAR, year);
		CALENDAR.set(Calendar.MONTH, month);
		CALENDAR.set(Calendar.DAY_OF_MONTH, day);
		return CALENDAR.getTime();
	}
}
