package com.udp.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
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
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_YEAR);

		String currentTimeStamp = String.valueOf(day) + String.valueOf(month) + String.valueOf(hours)
				+ String.valueOf(minutes) + String.valueOf(seconds);

		return currentTimeStamp;
	}
	
	/**
	 * Get from a String the date in format yyyy-MM-dd.
	 * 
	 * @param dateToProcess
	 *            the date from which to extract
	 * @return the date <code>String</code> in specified format
	 */
	public static String extractStringDateFromString(String dateToProcess) {

		return dateToProcess.substring(0, 10);
	}
	
	/**
	 * Get from a String the hour in format hh-mm-ss.
	 * 
	 * @param dateToProcess
	 *            the date from which to extract
	 * @return the hour <code>String</code> in specified format
	 */
	public static String extractStringHourFromString(String dateToProcess) {

		return dateToProcess.substring(dateToProcess.length() - 8, dateToProcess.length());
	}
	
	/**
	 * Get from a String the date in format yyyy-MM-dd.
	 * 
	 * @param dateToProcess
	 *            the date from which to extract
	 * @return the date <code>Date</code> in specified format
	 */
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
	
	/**
	 * Check if the two calendar dates are in the same day.
	 * 
	 * @param date_1
	 *            the first date
	 * @param date_2
	 *            the second date
	 * @return <code>true</code> or <code>false</code>
	 */
	public static boolean checkIfDatesAreInTheSameDay(String date_1, String date_2) {
		Date date1 = TimeHelper.extractDateFromString(date_1);
		Date date2 = TimeHelper.extractDateFromString(date_2);

		return DateUtils.isSameDay(date1, date2);
	}
	
	/**
	 * Check if the input date corresponds to the specified format.
	 * 
	 * @param dateToCheck
	 *            the <code>String</code> date to verify its correctness
	 * @return true or false
	 */
	public static boolean checkForRightDateTimeFormat(String dateToCheck) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			sdf.parse(dateToCheck);

			return true;
		} catch (ParseException ex) {
			log.error("Exception in checkForRightDateTimeForamt() function, TimeHelper class : " + ex.getMessage());

			return false;
		}
	}
	
	/**
	 * Check if the one calendar date is before the other.
	 * 
	 * @param date_1
	 *            the first date
	 * @param date_2
	 *            the second date
	 * @return <code>true</code> or <code>false</code>
	 */
	public static boolean checkIfDateOneIsBeforeDateTwo(String date_1, String date_2) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = sdf.parse(date_1);
			date2 = sdf.parse(date_2);
		} catch (ParseException e) {
			log.error("Exception in checkIfDateOneIsBeforeDateTwo() function, TimeHelper class : " + e.getMessage());
		}

		if (date1.before(date2)) {
			return true;
		} else {
			return false;
		}
	}
}
