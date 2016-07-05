package com.rest.api;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.database.DBQuery;
import com.model.Message;
import com.settings.ConfigurationsManager;
import com.udp.helper.Constants;
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
	 * @return a <code>JSONObject</code> containing the date of the day, the
	 *         average value and the maximum value of the light
	 */
	public static JSONObject getLuminosityStatisticsForDay(String date_1, String date_2) {
		HashMap<String, Integer> luminosity = new HashMap<String, Integer>();
		String detectedLuminosityAtTime = "";

		ArrayList<Message> todayEntries = DBQuery.getAllDataBetweenTwoTimeValues(date_1, date_2);

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
		
		String formatedDate = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_1);
		jsonObject.put("date", formatedDate);

		return jsonObject;
	}

	/**
	 * Remove from the map all the elements that are not equal with the maximum
	 * and the average value.
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
	 * Calculate the average value of the elements in the map.
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
			log.error("Exception in calculateAverage() function, SensorHistoryCriteria class : " + e.getMessage());
		}

		return average;
	}

	/**
	 * Get the total time spent in the room between two dates, ie the whole time
	 * when there was motion.
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
		String formatedDateOne = null;
		String formatedDateTwo = null;
		boolean readyForTimeDifferenceCalculation = false;
		boolean isStartTimeSeted = false;

		Iterator<Message> itr = todayEntries.iterator();
		while (itr.hasNext()) {
			Message message = itr.next();
			if (message.getPirSensorVal() && !isStartTimeSeted) {
				formatedDateOne = TimeHelper.getMessageTime("HH:mm:ss", message);
				readyForTimeDifferenceCalculation = false;
				isStartTimeSeted = true;
			} else if (!message.getPirSensorVal() && !message.isHeartbeat() && isStartTimeSeted) {
				formatedDateTwo = TimeHelper.getMessageTime("HH:mm:ss", message);
				readyForTimeDifferenceCalculation = true;
				isStartTimeSeted = false;
			}

			if (readyForTimeDifferenceCalculation) {
				try {
					timeSpent += TimeHelper.getDifference("HH:mm:ss", formatedDateOne, formatedDateTwo);
					readyForTimeDifferenceCalculation = false;
				} catch (ParseException e) {
					log.error("Exception in getTimeSpentInTheRoom() function, SensorHistoryCriteria class : " + e.getMessage());
				}
			}
		}

		return timeSpent;
	}

	/**
	 * Get the number of the motion detection during a time interval.
	 * 
	 * @param requiredDate
	 *            day that interests us
	 * @return the total number of motion detection
	 */
	public static int getMotionActivityForDay(String date_1, String date_2) {
		
		return DBQuery.getMotionActivityForDay(date_1, date_2, true);
	}

	/**
	 * Get the total time the light was turned on in the room between two time values.
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
		String formatedDateOne = null;
		String formatedDateTwo = null;
		boolean readyForTimeDifferenceCalculation = false;
		boolean isStartTimeSeted = false;

		ConfigurationsManager configManager = new ConfigurationsManager();
		int lightThreshold = Integer.parseInt(configManager.readConfigValue("lightThreshold"));

		Iterator<Message> itr = todayEntries.iterator();
		while (itr.hasNext()) {
			Message message = itr.next();
			if ((message.getLightSensorVal() > lightThreshold) && !isStartTimeSeted) {
				formatedDateOne = TimeHelper.getMessageTime("HH:mm:ss", message);
				isStartTimeSeted = true;
				readyForTimeDifferenceCalculation = false;
			} else if ((message.getLightSensorVal() < lightThreshold) && isStartTimeSeted) {
				formatedDateTwo = TimeHelper.getMessageTime("HH:mm:ss", message);
				isStartTimeSeted = false;
				readyForTimeDifferenceCalculation = true;
			}

			if (readyForTimeDifferenceCalculation) {
				try {
					timeSpent += TimeHelper.getDifference("HH:mm:ss", formatedDateOne, formatedDateTwo);
					readyForTimeDifferenceCalculation = false;
				} catch (ParseException e) {
					log.error("Exception in getTotalTimeLightOnInTheRoom() function, SensorHistoryCriteria class : " + e.getMessage());
				}
			}
		}

		return timeSpent;
	}
	
	/**
	 * Check for someone presence in the room. First, check if the last entry in
	 * the database has the pirSensorVal set and this is not a heartbeat
	 * message. If so, then it verifies the time difference between this last
	 * entry and the current time. If the time difference is less than the
	 * specified constant in the configuration file, then there is motion in the
	 * room. If the time difference is greater than the specified constant, then
	 * it takes from the database the 2 last pairs of entries that show the
	 * motion. Then it determines the time difference between the last but one
	 * and the last motion and the time difference between the last motion and
	 * the current time. If they are less that the specified constant then there
	 * is motion in the room, if not - no motion. Every verified condition for
	 * motion presence is accompanied by the light level checking, showing if
	 * the light is turned on or off.
	 * 
	 * @return a <code>JSONObject</code> with the values for the motion and
	 *         light status
	 */
	public static JSONObject checkForSomeoneInTheRoom() {
		JSONObject jsonObject = new JSONObject();

		String currentTime = TimeHelper.getCurrentTime();
		Message lastMessage = DBQuery.getLastEntry();

		if (lastMessage.getPirSensorVal()) {
			long timeDiffBetweenLastMotionAndCurrentTime = 0;
			try {
				timeDiffBetweenLastMotionAndCurrentTime = TimeHelper.getDifference("yyyy-MM-dd HH:mm:ss.S", currentTime,
						lastMessage.getTimeReceived());
			} catch (ParseException e) {
				log.error("Exception in checkForSomeoneInTheRoom() function, SensorHistoryCriteria class : "
						+ e.getMessage());
			}

			if (timeDiffBetweenLastMotionAndCurrentTime < Constants.MINIMUM_DELAY_TO_CHECK_PRESENCE_IN_ROOM) {
				jsonObject = setLightStateInJSON(lastMessage);
				jsonObject.put("isMotion", "yes");

				return jsonObject;
			}
		} else {
			ArrayList<Message> listWithMotionStartMoments = DBQuery.getDataSet(
					"SELECT * FROM sensor_data where pirSensorVal=true and isHeartBeat=false ORDER BY id DESC LIMIT 2");
			ArrayList<Message> listWithMotionEndMoments = DBQuery.getDataSet(
					"SELECT * FROM sensor_data where pirSensorVal=false and isHeartBeat=false ORDER BY id DESC LIMIT 2");
			long timeDiffBetweenLastButOneAndLastMotion = 0;
			long timeDiffBetweenLastMotionAndCurrentTime = 0;
			try {
				timeDiffBetweenLastButOneAndLastMotion = TimeHelper.getDifference("yyyy-MM-dd HH:mm:ss.S",
						listWithMotionEndMoments.get(1).getTimeReceived(),
						listWithMotionStartMoments.get(0).getTimeReceived());
				timeDiffBetweenLastMotionAndCurrentTime = TimeHelper.getDifference("yyyy-MM-dd HH:mm:ss.S",
						listWithMotionEndMoments.get(0).getTimeReceived(), currentTime);

				if (timeDiffBetweenLastButOneAndLastMotion > Constants.MINIMUM_DELAY_TO_CHECK_PRESENCE_IN_ROOM
						&& timeDiffBetweenLastMotionAndCurrentTime > Constants.MINIMUM_DELAY_TO_CHECK_PRESENCE_IN_ROOM) {
					jsonObject = setLightStateInJSON(lastMessage);
					jsonObject.put("isMotion", "no");

					return jsonObject;
				} else {
					jsonObject = setLightStateInJSON(lastMessage);
					jsonObject.put("isMotion", "yes");

					return jsonObject;
				}
			} catch (ParseException e) {
				log.error("Exception in checkForSomeoneInTheRoom() function, SensorHistoryCriteria class : "
						+ e.getMessage());
			}
		}

		return jsonObject;
	}
	
	/**
	 * Check if the input value of the light is greater or less than the
	 * threshold value form the configuration file.
	 * 
	 * @param valueToCompare
	 * @return true or false
	 */
	public static boolean checkForLightOn(int valueToCompare) {
		ConfigurationsManager configManager = new ConfigurationsManager();
		int lightThreshold = Integer.parseInt(configManager.readConfigValue("lightThreshold"));
		boolean lightStatus = false;

		lightStatus = (valueToCompare < lightThreshold) ? false : true;

		return lightStatus;
	}
	
	/**
	 * Check and set the light state in a <code>JSNOObject</code>.
	 * 
	 * @param lastMessage
	 *            the message from which to test the light value
	 * @return a <code>JSONObject</code> with <code>no</code> or
	 *         <code>yes</code> value
	 */
	public static JSONObject setLightStateInJSON(Message lastMessage) {
		JSONObject jsonObject = new JSONObject();

		if (checkForLightOn(lastMessage.getLightSensorVal())) {
			jsonObject.put("isLightOn", "yes");
		} else {
			jsonObject.put("isLightOn", "no");
		}

		return jsonObject;
	}
}
