package com.udp.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.rest.api.SensorHistoryCriteria;
import com.udp.io.Log4j;

/**
 * Contains static methods for time manipulation operations
 * 
 * @author sscerbatiuc
 */
public class TimeHelper {
	
	static Logger log = Log4j.initLog4j(TimeHelper.class);

	/**
	 * Returns the current time. <b>Format: </b>"yyyy-MM-dd HH:MM:SS"
	 * 
	 * @return String <code>currentTime</code>
	 */
	public static String getCurrentTime() {

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
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
		int month 	= calendar.get(Calendar.MONTH) + 1;
		int day 	= calendar.get(Calendar.DAY_OF_YEAR);

		String currentTimeStamp = String.valueOf(day) + String.valueOf(month)
				+ String.valueOf(hours) + String.valueOf(minutes)
				+ String.valueOf(seconds);

		return currentTimeStamp;

	}
	
	public static String extractStringDateFromString(String dateToProcess) {

		return dateToProcess.substring(0, 10);
	}
	
	public static String extractStringHourFromString(String dateToProcess) {
		
		return dateToProcess.substring(dateToProcess.length() - 8, dateToProcess.length()); 
	}
	
	public static Date extractDateFromString(String dateToProcess) {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = fmt.parse(dateToProcess);
		} catch (ParseException e) {
			log.error("Exception in extractDateFromString() function, TimeHelper class : " + e.getMessage());
		}
		return date;
	}
}
