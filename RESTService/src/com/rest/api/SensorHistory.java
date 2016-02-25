package com.rest.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.database.DBQuery;
import com.google.gson.JsonArray;
import com.model.Message;
import com.udp.helper.TimeHelper;
import com.udp.io.JsonService;
import com.udp.io.Log4j;

/**
 *
 * @author Nicolae
 * 
 *         Show the historical data about sensors' state. Get entries by
 *         specified criteria, convert them into a <code>JSONArray</code> and
 *         send to the client.
 */
@Path("/history")
public class SensorHistory {
	
	static Logger log = Log4j.initLog4j(SensorHistory.class);

	/**
	 * Get all entries from the database. Call like
	 * http://localhost:8080/RESTService/sensor/history/
	 * 
	 * @return a <code>JSONArray</code> containing all entries from the database
	 */
	@GET
	@Produces("application/json")
	public Response getSensorData() throws JSONException {
		try {
			ArrayList<Message> allData = DBQuery.getAllData();
			JsonArray jsonArray = JsonService.createJsonArray(allData);
			String result = "" + jsonArray;

			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			log.error("Exception in getSensorData() function, SensorHistory class : " + e.getMessage());

			return Response.status(500).build();
		}
	}

	/**
	 * Get last n entries from the database. Call like
	 * http://localhost:8080/RESTService/sensor/history/2
	 * 
	 * @param limit
	 *            the last n entries from the database
	 * @return a <code>JSONArray</code> containing the last n entries from the
	 *         database
	 */
	@GET
	@Path("{limit}")
	@Produces("application/json")
	public Response getLastXEntries(@PathParam("limit") int limit) {
		try {
			ArrayList<Message> allData = DBQuery.getLastXEntries(limit);
			JsonArray jsonArray = JsonService.createJsonArray(allData);
			String result = "" + jsonArray;

			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			log.error("Exception in getLastXEntries() function, SensorHistory class : " + e.getMessage());

			return Response.status(500).build();
		}
	}

	/**
	 * Get the total time the motion occurred in the room, between two dates.
	 * Call like
	 * http://localhost:8080/RESTService/sensor/history/timespent/2016-02-15
	 * 11:00:00&2016-02-15 18:00:00
	 * 
	 * @param date_1
	 *            first calendar date indicating when to begin calculation
	 * @param date_2
	 *            second calendar date indicating when to stop calculation
	 * @return a <code>JSONObject</code> containing the time in milliseconds for
	 *         each date in the specified interval
	 */
	@GET
	@Path("/timespent/{date_1}&{date_2}")
	@Produces("application/json")
	public Response getTotalTimeSpentInRoom(@PathParam("date_1") String date_1,
			@PathParam("date_2") String date_2) {
		JSONArray jsonArray = new JSONArray();
		if (TimeHelper.checkForRightDateTimeFormat(date_1) && TimeHelper.checkForRightDateTimeFormat(date_2)
				&& TimeHelper.checkIfDateOneIsBeforeDateTwo(date_1, date_2)) {
			if (TimeHelper.checkIfDatesAreInTheSameDay(date_1, date_2)) {
				long totalTime = SensorHistoryCriteria.getTimeSpentInTheRoom(date_1, date_2);
				jsonArray.put(JsonService.createJSONObject(TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_1),
						"timespent", totalTime));
				String result = "" + jsonArray;

				return Response.status(200).entity(result).build();
			} else {
				String startDateInterval = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_1);
				String endDateInterval = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_2);
				String startHour = TimeHelper.createDateTimeWithFormat("HH:mm:ss", date_1);
				String endHour = TimeHelper.createDateTimeWithFormat("HH:mm:ss", date_2);
				List<LocalDate> totalDates = TimeHelper.getDatesBetweenTwoDates(startDateInterval, endDateInterval);

				for (LocalDate localDate : totalDates) {
					long motionCount = SensorHistoryCriteria.getTimeSpentInTheRoom(localDate + " " + startHour,
							localDate + " " + endHour);
					jsonArray.put(JsonService.createJSONObject(localDate.toString(), "timespent", motionCount));
				}
				String result = "" + jsonArray;

				return Response.status(200).entity(result).build();
			}
		} else {
			log.error("Wrong date time format parameter in getTotalTimeSpentInRoom() function, SensorHistory class.");

			return Response.status(400).build();
		}
	}

	/**
	 * Get the average value and the maximum value of the light for the
	 * specified time interval. Call like
	 * http://localhost:8080/RESTService/sensor/history/luminosity/2016-02-15
	 * 08:00:00&2016-02-24 18:00:00
	 * 
	 * @return a <code>JSONArray</code> containing the maximum and the average
	 *         value of the light grouped by calendar date
	 */
	@GET
	@Path("/luminosity/{date_1}&{date_2}")
	@Produces("application/json")
	public Response getLuminosityStatistics(@PathParam("date_1") String date_1,
			@PathParam("date_2") String date_2) {
		JSONArray jsonArray = new JSONArray();
		if (TimeHelper.checkForRightDateTimeFormat(date_1) && TimeHelper.checkForRightDateTimeFormat(date_2)
				&& TimeHelper.checkIfDateOneIsBeforeDateTwo(date_1, date_2)) {
			if (TimeHelper.checkIfDatesAreInTheSameDay(date_1, date_2)) {
				JSONObject lightStatisticsForDay = SensorHistoryCriteria.getLuminosityStatisticsForDay(date_1, date_2);
				jsonArray.put(lightStatisticsForDay);
				String result = "" + jsonArray;

				return Response.status(200).entity(result).build();
			} else {
				String startDateInterval = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_1);
				String endDateInterval = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_2);
				String startHour = TimeHelper.createDateTimeWithFormat("HH:mm:ss", date_1);
				String endHour = TimeHelper.createDateTimeWithFormat("HH:mm:ss", date_2);

				List<LocalDate> totalDates = TimeHelper.getDatesBetweenTwoDates(startDateInterval, endDateInterval);
				for (LocalDate localDate : totalDates) {
					JSONObject lightStatisticsForDay = SensorHistoryCriteria
							.getLuminosityStatisticsForDay(localDate + " " + startHour, localDate + " " + endHour);
					jsonArray.put(lightStatisticsForDay);
				}
				String result = "" + jsonArray;

				return Response.status(200).entity(result).build();
			}
		} else {
			log.error("Wrong date time format parameter in getLuminosityStatistics() function, SensorHistory class.");

			return Response.status(400).build();
		}
	}

	/**
	 * Get the number of motion detection for the specified time interval. Call
	 * like http://localhost:8080/RESTService/sensor/history/motion/2016-02-15
	 * 08:00:00&2016-02-24 18:00:00
	 * 
	 * @return a <code>JSONArray</code> containing total number of the motion
	 *         detection grouped by calendar date
	 */
	@GET
	@Path("/motion/{date_1}&{date_2}")
	@Produces("application/json")
	public Response getMotionActivity(@PathParam("date_1") String date_1,
			@PathParam("date_2") String date_2) {
		JSONArray jsonArray = new JSONArray();
		if (TimeHelper.checkForRightDateTimeFormat(date_1) && TimeHelper.checkForRightDateTimeFormat(date_2)
				&& TimeHelper.checkIfDateOneIsBeforeDateTwo(date_1, date_2)) {
			if (TimeHelper.checkIfDatesAreInTheSameDay(date_1, date_2)) {
				int motionNumber = SensorHistoryCriteria.getMotionActivityForDay(date_1, date_2);
				jsonArray.put(JsonService.createJSONObject(TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_1),
						"activity", motionNumber));
				String result = "" + jsonArray;

				return Response.status(200).entity(result).build();
			} else {
				String startDateInterval = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_1);
				String endDateInterval = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_2);
				String startHour = TimeHelper.createDateTimeWithFormat("HH:mm:ss", date_1);
				String endHour = TimeHelper.createDateTimeWithFormat("HH:mm:ss", date_2);
				List<LocalDate> totalDates = TimeHelper.getDatesBetweenTwoDates(startDateInterval, endDateInterval);

				for (LocalDate localDate : totalDates) {
					int motionNumber = SensorHistoryCriteria.getMotionActivityForDay(localDate + " " + startHour,
							localDate + " " + endHour);
					jsonArray.put(JsonService.createJSONObject(localDate.toString(), "activity", motionNumber));
				}
				String result = "" + jsonArray;

				return Response.status(200).entity(result).build();
			}
		} else {
			log.error("Wrong date time format parameter in getMotionActivity() function, SensorHistory class.");

			return Response.status(400).build();
		}
	}
	
	/**
	 * Get the total time the light was turned on in the room between two dates.
	 * Call like
	 * http://localhost:8080/RESTService/sensor/history/lightOn/2016-02-15
	 * 11:00:00&2016-02-15 18:00:00
	 * 
	 * @param date_1
	 *            first calendar date indicating when to begin calculation
	 * @param date_2
	 *            second calendar date indicating when to stop calculation
	 * @return a <code>JSONObject</code> containing the total time in
	 *         milliseconds grouped by date
	 */
	@GET
	@Path("/lightOn/{date_1}&{date_2}")
	@Produces("application/json")
	public Response getLightOnStatistics(@PathParam("date_1") String date_1, @PathParam("date_2") String date_2) {
		JSONArray jsonArray = new JSONArray();
		if (TimeHelper.checkForRightDateTimeFormat(date_1) && TimeHelper.checkForRightDateTimeFormat(date_2)
				&& TimeHelper.checkIfDateOneIsBeforeDateTwo(date_1, date_2)) {
			if (TimeHelper.checkIfDatesAreInTheSameDay(date_1, date_2)) {
				try {
					long totalTime = SensorHistoryCriteria.getTotalTimeLightOnInTheRoom(date_1, date_2);
					jsonArray.put(JsonService.createJSONObject(
							TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_1), "totalTime", totalTime));
					String result = "" + jsonArray;

					return Response.status(200).entity(result).build();
				} catch (Exception e) {
					log.error("Exception in getLightOnPeriod() function, SensorHistory class : " + e.getMessage());

					return Response.status(500).build();
				}
			} else {
				String startDateInterval = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_1);
				String endDateInterval = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", date_2);
				String startHour = TimeHelper.createDateTimeWithFormat("HH:mm:ss", date_1);
				String endHour = TimeHelper.createDateTimeWithFormat("HH:mm:ss", date_2);
				List<LocalDate> totalDates = TimeHelper.getDatesBetweenTwoDates(startDateInterval, endDateInterval);
				for (LocalDate localDate : totalDates) {
					long totalTime = SensorHistoryCriteria.getTotalTimeLightOnInTheRoom(localDate + " " + startHour,
							localDate + " " + endHour);
					jsonArray.put(JsonService.createJSONObject(localDate.toString(), "totalTime", totalTime));
				}
				String result = "" + jsonArray;
				return Response.status(200).entity(result).build();
			}
		} else {
			log.error("Wrong date time format parameter in getLightOnStatistics() function, SensorHistory class.");

			return Response.status(400).build();
		}
	}
}
