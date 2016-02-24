package com.udp.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

import com.model.Message;
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
		int milliseconds = calendar.get(Calendar.MILLISECOND);
		String currentTimeString = year + "-" + month + "-" + day + " "
				+ (hours < 10 ? "0" + hours : hours) + ":"
				+ (minutes < 10 ? "0" + minutes : minutes) + ":"
				+ (seconds < 10 ? "0" + seconds : seconds) + "."
				+ (milliseconds);
		
		return currentTimeString;
	}
	
	/**
	 * Returns the current time. <b>Format: </b>"yyyy-MM-dd"
	 * 
	 * @return String <code>currentTime</code>
	 */
	public static String getCurrentDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		String currentTime = dateFormat.format(calendar.getTime());

		return currentTime;
	}
	
	/**
	 * Returns the current time. <b>Format: </b>"yyyy-MM-dd"
	 * 
	 * @return String <code>currentTime</code>
	 */
	public static String getXDayAgoDateCurrentDate(int daysAgo) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance(Locale.getDefault());
		calendar.add(Calendar.DATE, - daysAgo);
		String xDaysAgoDate = dateFormat.format(calendar.getTime());

		return xDaysAgoDate;
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
	 * Returns the number of milliseconds that have passed since a given date.
	 * 
	 * @param inspectedDateString
	 *            <code>String</code> - The inspected date
	 * @return difference <code>long</code> - How much time has passed since the
	 *         inspected date
	 * @throws ParseException
	 *             if the provided date string is not an acceptable format
	 */
	public static long howMuchMillisSince(String inspectedDateString)
			throws ParseException {

		SimpleDateFormat sdtFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		Date currentTime = sdtFormat.parse(TimeHelper.getCurrentTime());
		Date inspectedTime = sdtFormat.parse(inspectedDateString);
		long difference = currentTime.getTime() - inspectedTime.getTime();
		return difference;

	}
	
	/**
	 * Returns the number of milliseconds between 2 given dates
	 * 
	 * @param date1
	 * @param date2
	 * @return difference <code>long</code> - How much time has passed between 2
	 *         given dates
	 * @throws ParseException
	 *             if the provided date string is not an acceptable format
	 */
	public static long getDifference(String date1, String date2)
			throws ParseException {

		SimpleDateFormat sdtFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss.S");
		Date currentTime = sdtFormat.parse(date1);
		Date inspectedTime = sdtFormat.parse(date2);
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentTime);;
		long difference = currentTime.getTime() - inspectedTime.getTime();
		return difference;
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
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = formatter.parse(TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_1));
			date2 = formatter.parse(TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_2));
		} catch (ParseException e) {
			log.error("Exception in checkIfDatesAreInTheSameDay() function, TimeHelper class : " + e.getMessage());
		}

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
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	
	/**
	 * Extract from a <code>Date String</code> the value in the specified
	 * format.
	 * 
	 * @param format
	 *            the format to create the date like "2016-02-24" or the time
	 *            like "08:00:00"
	 * @param dateToProcess
	 *            the <code>String</code> containing the <code>DateTime</code>
	 *            value
	 * @return the date or the hour according to the specified format
	 */
	public static String createDateTimeWithFormat(String format, String dateToProcess) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateToProcess);
		} catch (ParseException e) {
			log.error("Exception in createDateTimeWithFormat() function, TimeHelper class : " + e.getMessage());
		}

		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * Extract from a <code>Message</code> the <code>DateTime</code> value in
	 * the specified format.
	 * 
	 * @param format
	 *            the format to create the date like "2016-02-24" or the time
	 *            like "08:00:00"
	 * @param message
	 *            the <code>Message</code> containing the <code>DateTime</code>
	 *            value
	 * @return the date or the hour according to the specified format
	 */
	public static Date getMessageTime(String format, Message message) {
		Date messageDate = null;
		try {
			messageDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(message.getTimeReceived());
		} catch (ParseException e) {
			log.error("Exception in createDateTimeWithFormat() function, TimeHelper class : " + e.getMessage());
		}

		String stringDate = new SimpleDateFormat(format).format(messageDate);
		Date date = null;
		try {
			date = new SimpleDateFormat(format).parse(stringDate);
		} catch (ParseException e) {
			log.error("Exception in createDateTimeWithFormat() function, TimeHelper class : " + e.getMessage());
			e.printStackTrace();
		}

		return date;
	}
}
