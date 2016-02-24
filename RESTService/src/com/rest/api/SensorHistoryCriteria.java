package com.rest.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.database.DBQuery;
import com.model.Message;
import com.settings.ConfigurationsManager;
import com.udp.helper.TimeHelper;
import com.udp.io.Log4j;

/**
 * 
 * @author Nicolae
 *
 *         All methods concerning the representation of the statistical data.
 */
public class SensorHistoryCriteria {

	static Logger log = Log4j.initLog4j(SensorHistoryCriteria.class);

	/**
	 * Get the maximum and the average value of the light in the room.
	 * 
	 * @param requiredDate
	 *            the calendar date that interest us to view the statistics
	 * @return a map <code>HashMap<String, Integer></code> with data like
	 *         "11:00:00-111" where 11:00:00 is the time when the light value
	 *         was captured and 111 is the value of the light in lux
	 * 
	 */
	public static JSONObject getLuminosityStatisticsForDay(String requiredDate) {
		HashMap<String, Integer> luminosity = new HashMap<String, Integer>();
		String detectedLuminosityAtTime = "";

		ArrayList<Message> todayEntries = DBQuery.getAllDataByParameter(requiredDate);

		Iterator<Message> itr = todayEntries.iterator();
		while (itr.hasNext()) {
			Message message = itr.next();
			detectedLuminosityAtTime = message.getTimeReceived().substring(message.getTimeReceived().length() - 10,
					message.getTimeReceived().length() - 2);
			luminosity.put(detectedLuminosityAtTime, message.getLightSensorVal());
		}

		filterLuminosityStatisticsMap(luminosity);

		JSONObject jsonObject = new JSONObject();
		for (Iterator<Map.Entry<String, Integer>> it = luminosity.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		jsonObject.put("date", requiredDate);

		return jsonObject;
	}

	/**
	 * Remove from the map all the elements that are not equal with the max
	 * value in the map.
	 * 
	 * @param map
	 *            the map to filter
	 * @return a filtered map containing the maximum and the average value
	 */
	public static HashMap<String, Integer> filterLuminosityStatisticsMap(HashMap<String, Integer> map) {
		if (!map.isEmpty()) {
			Integer maxValueInMap = (Collections.max(map.values()));
			Integer averageValueInMap = (int) calculateAverage(map);

			map.clear();

			map.put("maxValue", maxValueInMap);
			map.put("avgValue", averageValueInMap);
		} else {
			map.put("maxValue", 0);
			map.put("avgValue", 0);
		}

		return map;
	}

	/**
	 * Calculate the average of elements in the map.
	 * 
	 * @param map
	 *            the map containing the elements to calculate
	 * @return the average value
	 */
	public static double calculateAverage(HashMap<String, Integer> map) {
		double average = 0.0;
		int counter = 0;
		int sum = 0;
		for (Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sum += entry.getValue();
			counter++;
		}

		try {
			average = sum / counter;
		} catch (Exception e) {
			log.error("Exception in calculateAverage() function, SensorHistory class : " + e.getMessage());
		}

		return average;
	}

	/**
	 * Get the total time spent in the room between two dates, ie the whole time
	 * when there was movement.
	 * 
	 * @param date_1
	 *            first calendar date indicating when to begin calculation
	 * @param date_2
	 *            second calendar date indicating when to stop calculation
	 * @return the time spent in milliseconds
	 */
	public static long getTimeSpentInTheRoom(String date_1, String date_2) {
		long timeSpent = 0;
		ArrayList<Message> todayEntries = DBQuery.getAllDataBetweenTwoTimeValues(date_1, date_2);
		Date l_date_1 = null;
		Date l_date_2 = null;
		boolean readyForTimeDifferenceCalculation = false;
		boolean isStartTimeSeted = false;

		Iterator<Message> itr = todayEntries.iterator();
		while (itr.hasNext()) {
			Message message = itr.next();
			if (message.getPirSensorVal() && !isStartTimeSeted) {
				l_date_1 = TimeHelper.createDateTimeWithFormat("HH:mm:ss", message);
				readyForTimeDifferenceCalculation = false;
				isStartTimeSeted = true;
			} else if (!message.getPirSensorVal() && !message.isHeartbeat()) {
				l_date_2 = TimeHelper.createDateTimeWithFormat("HH:mm:ss", message);
				readyForTimeDifferenceCalculation = true;
				isStartTimeSeted = false;
			}

			if (readyForTimeDifferenceCalculation) {
				timeSpent += calculateTimeDifference(l_date_1, l_date_2);
			}
		}

		return timeSpent;
	}

	/**
	 * Calculate the time difference between two time values.
	 * 
	 * @param date_1
	 *            first time value
	 * @param date_2
	 *            second time value
	 * @return the time difference in milliseconds
	 */
	public static long calculateTimeDifference(Date date_1, Date date_2) {

		return date_2.getTime() - date_1.getTime();
	}

	/**
	 * Get the average value and the maximum value of the light for each day
	 * during the last week.
	 * 
	 * @return a <code>JSONArray</code> containing the maximum and the average
	 *         value of the light grouped by calendar date
	 */
	public static JSONArray getLuminosityStatisticsForLastWeek() {
		JSONArray jsonArray = new JSONArray();

		String currentDate = TimeHelper.getCurrentDate();
		String sevenDaysAgoDate = TimeHelper.getXDayAgoDateCurrentDate(7);

		List<LocalDate> totalDates = getDatesBetweenTwoDates(sevenDaysAgoDate, currentDate);

		for (LocalDate localDate : totalDates) {
			JSONObject jsonObject = SensorHistoryCriteria.getLuminosityStatisticsForDay(localDate.toString());
			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	/**
	 * Get all the calendar dates between two dates.
	 * 
	 * @param sevenDaysAgoDate
	 *            the date from the past from which to start the identification
	 *            of the dates
	 * @param currentDate
	 *            the today's date
	 * @return a list with all the dates between the specified interval
	 */
	public static List<LocalDate> getDatesBetweenTwoDates(String sevenDaysAgoDate, String currentDate) {
		LocalDate start = LocalDate.parse(sevenDaysAgoDate);
		LocalDate end = LocalDate.parse(currentDate);
		List<LocalDate> totalDates = new ArrayList<>();
		while (!start.isAfter(end)) {
			totalDates.add(start);
			start = start.plusDays(1);
		}

		return totalDates;
	}

	/**
	 * Get the number of the motion detection during a day.
	 * 
	 * @param requiredDate
	 *            day that interests us
	 * @return the total number of motion detection
	 */
	public static JSONObject getMotionActivityForDay(String requiredDate) {
		int todayActivity = DBQuery.getMotionActivityForDay(requiredDate, true);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("date", requiredDate);
		jsonObject.put("activity", todayActivity);

		return jsonObject;
	}

	/**
	 * Get the number of the motion detection for the last week grouped by day.
	 * 
	 * @return a <code>JSONArray</code> containing the total number of the
	 *         motion detection grouped by calendar date
	 */
	public static JSONArray getMotionActivityForLastWeek() {
		JSONArray jsonArray = new JSONArray();

		String currentDate = TimeHelper.getCurrentDate();
		String sevenDaysAgoDate = TimeHelper.getXDayAgoDateCurrentDate(7);

		List<LocalDate> totalDates = getDatesBetweenTwoDates(sevenDaysAgoDate, currentDate);

		for (LocalDate localDate : totalDates) {
			JSONObject jsonObject = SensorHistoryCriteria.getMotionActivityForDay(localDate.toString());
			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	/**
	 * Get the total time the light was on in the room between two time values.
	 * 
	 * @param date_1
	 *            first calendar date indicating when to begin calculation
	 * @param date_2
	 *            second calendar date indicating when to stop calculation
	 * @return the time spent in milliseconds
	 */
	public static long getTotalTimeLightOnInTheRoom(String date_1, String date_2) {
		long timeSpent = 0;
		ArrayList<Message> todayEntries = DBQuery.getAllDataBetweenTwoTimeValues(date_1, date_2);
		Date l_date_1 = null;
		Date l_date_2 = null;
		boolean readyForTimeDifferenceCalculation = false;
		boolean isStartTimeSeted = false;

		ConfigurationsManager configManager = new ConfigurationsManager();
		int lightThreshold = Integer.parseInt(configManager.readConfigValue("lightThreshold"));

		Iterator<Message> itr = todayEntries.iterator();
		while (itr.hasNext()) {
			Message message = itr.next();
			if ((message.getLightSensorVal() > lightThreshold) && !isStartTimeSeted) {
				l_date_1 = TimeHelper.createDateTimeWithFormat("HH:mm:ss", message);
				isStartTimeSeted = true;
				readyForTimeDifferenceCalculation = false;
			} else if ((message.getLightSensorVal() < lightThreshold) && isStartTimeSeted) {
				l_date_2 = TimeHelper.createDateTimeWithFormat("HH:mm:ss", message);
				isStartTimeSeted = false;
				readyForTimeDifferenceCalculation = true;
			}

			if (readyForTimeDifferenceCalculation) {
				timeSpent += calculateTimeDifference(l_date_1, l_date_2);
			}
		}

		return timeSpent;
	}

//	/**
//	 * Extract from the message the time in HH:MM:SS format.
//	 * 
//	 * @param message
//	 *            the message to process
//	 * @return the value of the time
//	 */
//	public static Date getMessageTime(Message message) {
//		Date date = null;
//		String timeMoment = "";
//		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
//		try {
//			timeMoment = TimeHelper.createDateTimeWithFormat("HH:mm:ss", message);
//			date = sdf.parse(timeMoment);
//		} catch (ParseException e) {
//			log.error("Exception in getMessageTime() function, SensorHistoryUtils class : "
//					+ e.getMessage());
//			e.printStackTrace();
//		}
//
//		return date;
//	}
}
