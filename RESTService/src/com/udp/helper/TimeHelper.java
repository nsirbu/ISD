package com.udp.helper;

import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.udp.io.Log4j;

/**
 * Contains static methods for time manipulation operations
 * 
 * @author sscerbatiuc
 */
public class TimeHelper {
	
	static Logger log = Log4j.initLog4j(TimeHelper.class);

	/**
	 * Returns the current time. <b>Format: </b>"yyyy-MM-dd HH:m:s.S"
	 * 
	 * @return String <code>currentTime</code>
	 */
	public static String getCurrentTime() {

		Calendar calendar = Calendar.getInstance();
		int year 		  = calendar.get(Calendar.YEAR);
		int month		  = calendar.get(Calendar.MONTH) + 1;
		int day 		  = calendar.get(Calendar.DAY_OF_MONTH);
		int hours 		  = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes 	  = calendar.get(Calendar.MINUTE);
		int seconds		  = calendar.get(Calendar.SECOND);
		int milliseconds  = calendar.get(Calendar.MILLISECOND);
		String currentTimeString = year + "-" + month + "-" + day + " "
				+ (hours < 10 ? "0" + hours : hours) + ":"
				+ (minutes < 10 ? "0" + minutes : minutes) + ":"
				+ (seconds < 10 ? "0" + seconds : seconds) + "."
				+ (milliseconds);
		return currentTimeString;
	}
	
	/**
	 * Returns the current date. <b>Format: </b>"yyyy-MM-dd"
	 * @param pattern TODO
	 * 
	 * @return String <code>currentTime</code>
	 */
	public static String getCurrentDate(String pattern) {

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();		
		String currentDate = dateFormat.format(calendar.getTime());
				
		return currentDate;
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

	/**
	 * Returns the number of milliseconds that have passed since a given date.
	 * @param inspectedDateString <code>String</code> - The inspected date
	 * @return difference <code>long</code> - How much time has passed since the inspected date 
	 * @throws ParseException if the provided date string is not an acceptable format
	 */
	public static long howMuchMillisSince(String inspectedDateString) throws ParseException{
		
		SimpleDateFormat sdtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date currentTime   = sdtFormat.parse(TimeHelper.getCurrentTime());
		Date inspectedTime = sdtFormat.parse(inspectedDateString);
		long difference	   = currentTime.getTime() - inspectedTime.getTime();
		return difference;
			
	}
	
	/**
	 * Returns the number of milliseconds between 2 given dates
	 * @param date1
	 * @param date2
	 * @return difference <code>long</code> - How much time has passed between 2 given dates
	 * @throws ParseException if the provided date string is not an acceptable format
	 */
	public static long getDifference(String date1, String date2) throws ParseException{
		
		SimpleDateFormat sdtFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date currentTime   = sdtFormat.parse(date1);
		Date inspectedTime = sdtFormat.parse(date2);
		long difference	   = currentTime.getTime() - inspectedTime.getTime();
		return difference;
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
