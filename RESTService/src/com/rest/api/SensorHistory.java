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
	
	// TODO Check if dates are in correct order
	
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
	 * Get the time spent in the room between two dates. Call like
	 * http://localhost:8080/RESTService/sensor/history/timespent/2016-02-15
	 * 11:00:00&2016-02-15 18:00:00
	 * 
	 * @param date_1
	 *            first calendar date indicating when to begin calculation
	 * @param date_2
	 *            second calendar date indicating when to stop calculation
	 * @return a <code>JSONObject</code> containing the time in milliseconds
	 */
	@GET
	@Path("/timespent/{date_1}&{date_2}")
	@Produces("application/json")
	public Response getMinMaxTimeSomebodyInRoom(@PathParam("date_1") String date_1,
			@PathParam("date_2") String date_2) {
		if (TimeHelper.checkForRightDateTimeFormat(date_1) && TimeHelper.checkForRightDateTimeFormat(date_2)
				&& TimeHelper.checkIfDateOneIsBeforeDateTwo(date_1, date_2)) {
			try {
				long time = SensorHistoryCriteria.getTimeSpentInTheRoom(date_1, date_2);
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("timeSpent", time);

				String result = "" + jsonObject;

				return Response.status(200).entity(result).build();
			} catch (Exception e) {
				log.error(
						"Exception in getMinMaxTimeSomebodyInRoom() function, SensorHistory class : " + e.getMessage());

				return Response.status(500).build();
			}
		} else {
			log.error("Wrong date time format");

			return Response.status(400).build();
		}
	}

	/**
	 * Get the maximum and the average value of the light in the room. Call like
	 * http://localhost:8080/RESTService/sensor/history/luminosity/2016-02-15
	 * 
	 * @param date
	 *            calendar date to show the statistics
	 * @return a <code>JSONArray</code> containing the maximum and the average
	 *         value of the light
	 */
	@GET
	@Path("/luminosity/{date}")
	@Produces("application/json")
	public Response getLuminosityStatistics(@PathParam("date") String date) {
		if (TimeHelper.checkForRightDateTimeFormat(date)) {
			try {
				String result = "" + SensorHistoryCriteria.getLuminosityStatisticsForDay(date);

				return Response.status(200).entity(result).build();
			} catch (Exception e) {
				log.error("Exception in getLuminosityStatistics() function, SensorHistory class : " + e.getMessage());

				return Response.status(500).build();
			}
		} else {
			log.error("Wrong date time format");

			return Response.status(400).build();
		}
	}

	/**
	 * Get the average value and the maximum value of the light for each day
	 * during the last week. Call like
	 * http://localhost:8080/RESTService/sensor/history/luminosity/lastweek
	 * 
	 * @return a <code>JSONArray</code> containing the maximum and the average
	 *         value of the light grouped by calendar date
	 */
	@GET
	@Path("/luminosity/lastweek")
	@Produces("application/json")
	public Response getLuminosityStatisticsForLastWeek() {
		try {
			String result = "" + SensorHistoryCriteria.getLuminosityStatisticsForLastWeek();

			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			log.error("Exception in getLuminosityStatisticsForLastWeek() function, SensorHistory class : "
					+ e.getMessage());

			return Response.status(500).build();
		}
	}

	/**
	 * Get the number of motion detection for each day during the last week.
	 * Call like
	 * http://localhost:8080/RESTService/sensor/history/motion/lastweek
	 * 
	 * @return a <code>JSONArray</code> containing total number of the motion
	 *         detection grouped by calendar date
	 */
	@GET
	@Path("/motion/lastweek")
	@Produces("application/json")
	public Response getMotionActivityForLastWeek() {
		try {
			String result = "" + SensorHistoryCriteria.getMotionActivityForLastWeek();

			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			log.error("Exception in getMotionActivityForLastWeek() function, SensorHistory class : " + e.getMessage());

			return Response.status(500).build();
		}
	}
	
	/**
	 * Get the total time the light was on in the room between two dates. Call
	 * like http://localhost:8080/RESTService/sensor/history/lightOn/2016-02-15
	 * 11:00:00&2016-02-15 18:00:00
	 * 
	 * @param date_1
	 *            first calendar date indicating when to begin calculation
	 * @param date_2
	 *            second calendar date indicating when to stop calculation
	 * @return a <code>JSONObject</code> containing the total time in
	 *         milliseconds
	 */
	@GET
	@Path("/lightOn/{date_1}&{date_2}")
	@Produces("application/json")
	public Response getLightOnDuringWeek(@PathParam("date_1") String date_1, @PathParam("date_2") String date_2) {
		JSONArray jsonArray = new JSONArray();
		if (TimeHelper.checkForRightDateTimeFormat(date_1) && TimeHelper.checkForRightDateTimeFormat(date_2)
				&& TimeHelper.checkIfDateOneIsBeforeDateTwo(date_1, date_2)) {
			if (TimeHelper.checkIfDatesAreInTheSameDay(date_1, date_2)) {
				try {
					String startDateInterval = TimeHelper.extractStringDateFromString(date_1);
					long totalTime = SensorHistoryCriteria.getTotalTimeLightOnInTheRoom(date_1, date_2);
					jsonArray.put(JsonService.createJSONWithLightOnStatisticsDataDuringDay(startDateInterval,
							totalTime + ""));
					String result = "" + jsonArray;

					return Response.status(200).entity(result).build();
				} catch (Exception e) {
					log.error("Exception in getLightOnPeriod() function, SensorHistory class : " + e.getMessage());

					return Response.status(500).build();
				}
			} else {
				String startDateInterval = TimeHelper.extractStringDateFromString(date_1);
				String endDateInterval = TimeHelper.extractStringDateFromString(date_2);
				String startHour = TimeHelper.extractStringHourFromString(date_1);
				String endHour = TimeHelper.extractStringHourFromString(date_2);
				List<LocalDate> totalDates = SensorHistoryCriteria.getDatesBetweenTwoDates(startDateInterval,
						endDateInterval);
				for (LocalDate localDate : totalDates) {
					long totalTime = SensorHistoryCriteria.getTotalTimeLightOnInTheRoom(localDate + " " + startHour,
							localDate + " " + endHour);
					jsonArray.put(JsonService.createJSONWithLightOnStatisticsDataDuringDay(startDateInterval,
							totalTime + ""));
				}
				String result = "" + jsonArray;
				return Response.status(200).entity(result).build();
			}
		} else {
			log.error("Wrong date time format");

			return Response.status(400).build();
		}
	}
}
