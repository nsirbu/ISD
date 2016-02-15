package com.udp.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Contains static methods for time manipulation operations
 * 
 * @author sscerbatiuc
 */
public class TimeHelper {

	/**
	 * Returns the current time. <b>Format: </b>"yyyy-MM-dd HH:MM:SS"
	 * 
	 * @return String <code>currentTime</code>
	 */
	public static String getCurrentTime() {

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		String currentTimeString = year + "-" + month + "-" + day + " "
				+ (hours < 10 ? "0" + hours : hours) + ":"
				+ (minutes < 10 ? "0" + minutes : minutes) + ":"
				+ (seconds < 10 ? "0" + seconds : seconds);
		return currentTimeString;
	}
	
	/**
	 * Returns the current time. <b>Format: </b>"yyyy-MM-dd"
	 * 
	 * @return String <code>currentTime</code>
	 */
	public static String getCurrentDate() {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();		
		String currentTime = dateFormat.format(calendar.getTime());
				
		return currentTime;
	}

	/**
	 * Returns a textual representation of the current time.
	 *
	 * @return String currentTimeStamp - day + month + hours + minutes
	 */
	public static String getCurrentTimeStamp() {
		Calendar calendar = Calendar.getInstance();
		int hours	= calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		int month 	= calendar.get(Calendar.MONTH);
		int day 	= calendar.get(Calendar.DAY_OF_YEAR);

		String currentTimeStamp = String.valueOf(day) + String.valueOf(month)
				+ String.valueOf(hours) + String.valueOf(minutes)
				+ String.valueOf(seconds);

		return currentTimeStamp;

	}
}
