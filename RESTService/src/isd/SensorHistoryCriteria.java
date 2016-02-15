package isd;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.udp.io.Log4j;

import isd.database.DBQuery;
import isd.model.Message;

/**
 *  
 * @author Nicolae
 *
 */
public class SensorHistoryCriteria {
	
	static Logger log = Log4j.initLog4j(SensorHistoryCriteria.class);
	
	/*
	 * Get the maximum and minimum time when somebody was in the room.
	 * 
	 * @param requiredDate the calendar date that interest us to view the statistics
	 * return              a map <code>HashMap<String, Long></code> with data like "11:00:00-20000"
	 *                     where 11:00:00 is the time when the motion was detected and
	 *                     20000 is the time spent in ms between the moment when the motion was detected
	 *                     and ended 
	 * 
	 */
	public static HashMap<String, Long> getMinMaxTimeSomebodyInRoom(String requiredDate) {
		HashMap<String, Long> time = new HashMap<String, Long>();
		Date date_1 = null;
		Date date_2 = null;
		boolean readyForTimeDifferenceCalculation = false;
		boolean isStartTimeSeted = false;
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");	
		
		ArrayList<Message> todayEntries = DBQuery.getAllDataByParameter(requiredDate);
		String detectedMotionAtTime = "";
		String endedMotionAtTime = "";
		
		Iterator<Message> itr = todayEntries.iterator();
		while (itr.hasNext()) {
			Message message =  itr.next();			
			if (message.getPirSensorVal() && !isStartTimeSeted) {					
				try {
					detectedMotionAtTime = message.getTimeReceived().substring(message.getTimeReceived().length()-10, message.getTimeReceived().length()-2);				    
					date_1 = sdf.parse(detectedMotionAtTime);
					readyForTimeDifferenceCalculation = false;
					isStartTimeSeted = true;
				} catch (ParseException e) {
					log.error("Exception in getMinMaxTimeSomebodyInRoom() function, SensorHistoryUtils class : " + e.getMessage());
					e.printStackTrace();
				}
			} else if (!message.getPirSensorVal()) {
				try {
					endedMotionAtTime = message.getTimeReceived().substring(message.getTimeReceived().length()-10, message.getTimeReceived().length()-2);	
					date_2 = sdf.parse(endedMotionAtTime);
					readyForTimeDifferenceCalculation = true;
					isStartTimeSeted = false;
				} catch (ParseException e) {
					log.error("Exception in getMinMaxTimeSomebodyInRoom() function, SensorHistoryUtils class : " + e.getMessage());
					e.printStackTrace();
				}
			}
			
			if (readyForTimeDifferenceCalculation) {
				calculateTimeDifference(date_1, date_2, time, detectedMotionAtTime);	
			}
		}				
		filterMotionStatisticsMap(time);
		
		return time;
	}
	
	/*
	 * Remove from the map all the elements that are not equal with the min and max value in the map.
	 * 
	 * @param HashMap<String, Long> map the map to filter
	 * return                       a filtered map
	 */
	public static HashMap<String, Long> filterMotionStatisticsMap(HashMap<String, Long> map) {
		long maxValueInMap = (Collections.max(map.values()));
		long minValueInMap = (Collections.min(map.values()));

		for (Iterator<Map.Entry<String, Long>> it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Long> entry = it.next();
			if (!entry.getValue().equals(maxValueInMap) && !entry.getValue().equals(minValueInMap)) {
				it.remove();
			}
		}

		return map;
	}
	
	/*
	 * Calculate the time difference between two time values.
	 * 
	 * @param date_1               first time value
	 * @param date_2               second time value
	 * @param time                 the map to put the time difference having as key the <code>detectedMotionAtTime</code> 
	 * @param detectedMotionAtTime time when the motion was detected
	 * return 					   time difference in ms
	 */
	public static long calculateTimeDifference(Date date_1, Date date_2, HashMap<String, Long> time, String detectedMotionAtTime) {
		long difference = 0;
		difference = date_2.getTime() - date_1.getTime();
		time.put(detectedMotionAtTime, difference);

		return difference;
	}
	
	/*
	 * Get the maximum and the average value of the light in the room.
	 * 
	 * @param requiredDate the calendar date that interest us to view the statistics
	 * return              a map <code>HashMap<String, Integer></code> with data like "11:00:00-111"
	 *                     where 11:00:00 is the time when the light value was captured and
	 *                     111 is the value of the light in lux 
	 * 
	 */
	public static HashMap<String, Integer> getLuminosityStatistics(String requiredDate) {
		HashMap<String, Integer> luminosity = new HashMap<String, Integer>();
		String detectedLuminosityAtTime = "";
		
		ArrayList<Message> todayEntries = DBQuery.getAllDataByParameter(requiredDate);
		
		Iterator<Message> itr = todayEntries.iterator();
	      while(itr.hasNext()) {
	    	  Message message =  itr.next();	    	  
	    	  detectedLuminosityAtTime = message.getTimeReceived().substring(message.getTimeReceived().length()-10, message.getTimeReceived().length()-2);
	    	  luminosity.put(detectedLuminosityAtTime, message.getLightSensorVal());
	      }
	      
	    filterLuminosityStatisticsMap(luminosity);
		
		return luminosity;
	}
	
	/*
	 * Remove from the map all the elements that are not equal with the max value in the map.
	 * 
	 * @param HashMap<String, Integer> map the map to filter
	 * return                          a filtered map
	 */
	public static HashMap<String, Integer> filterLuminosityStatisticsMap(HashMap<String, Integer> map) {
		Integer maxValueInMap = (Collections.max(map.values()));
		Integer averageValueInMap = (int) calculateAverage(map);
		map.put("average", averageValueInMap);

		for (Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			if (!entry.getValue().equals(maxValueInMap) && !entry.getValue().equals(averageValueInMap)) {
				it.remove();
			}
		}

		return map;
	}
	
	/*
	 * Calculate the average of the light value.
	 * 
	 * @param HashMap<String, Integer> map the map containing the elements to
	 * calculate return average value
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

		average = sum / counter;

		return average;
	}
	
	/*
	 * Get the total time spent in the room, ie the whole time when there was movement.
	 * 
	 * @param date_1 first calendar date indicating when to begin calculation
	 * @param date_2 second calendar date indicating when to stop calculation
	 * return        time spent in ms
	 */
	public static long getTimeSpentInTheRoom(String date_1, String date_2) {
		long timeSpent = 0;
		ArrayList<Message> todayEntries = DBQuery.getAllDataBetweenTwoTimeValues(date_1, date_2);
		Date l_date_1 = null;
		Date l_date_2 = null;
		boolean readyForTimeDifferenceCalculation = false;
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");	
		
		String detectedMotionAtTime = "";
		String endedMotionAtTime = "";
		
		Iterator<Message> itr = todayEntries.iterator();
		while (itr.hasNext()) {
			Message message =  itr.next();			
			if (message.getPirSensorVal()) {					
				try {
					detectedMotionAtTime = message.getTimeReceived().substring(message.getTimeReceived().length()-10, message.getTimeReceived().length()-2);				    
					l_date_1 = sdf.parse(detectedMotionAtTime);
					readyForTimeDifferenceCalculation = false;
				} catch (ParseException e) {
					log.error("Exception in getMinMaxTimeSomebodyInRoom() function, SensorHistoryUtils class : " + e.getMessage());
					e.printStackTrace();
				}
			} else {
				try {
					endedMotionAtTime = message.getTimeReceived().substring(message.getTimeReceived().length()-10, message.getTimeReceived().length()-2);	
					l_date_2 = sdf.parse(endedMotionAtTime);
					readyForTimeDifferenceCalculation = true;
				} catch (ParseException e) {
					log.error("Exception in getMinMaxTimeSomebodyInRoom() function, SensorHistoryUtils class : " + e.getMessage());
					e.printStackTrace();
				}
			}
			
			if (readyForTimeDifferenceCalculation) {
				timeSpent += calculateTimeDifference(l_date_1, l_date_2);	
			}
		}				
		
		return timeSpent;
	}
	
	/*
	 * Calculate the time difference between two time values.
	 * 
	 * @param date_1 first time value
	 * @param date_2 second time value
	 * return 		 time difference in ms
	 */
	public static long calculateTimeDifference(Date date_1, Date date_2) {

		return date_2.getTime() - date_1.getTime();
	}
}
