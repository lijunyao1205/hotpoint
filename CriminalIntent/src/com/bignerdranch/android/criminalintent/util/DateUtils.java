/**
 * 
 */
package com.bignerdranch.android.criminalintent.util;

import android.annotation.TargetApi;
import android.os.Build;
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
}
