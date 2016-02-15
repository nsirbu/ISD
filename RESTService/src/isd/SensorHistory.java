package isd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.udp.io.JsonService;

import isd.database.DBQuery;
import isd.model.Message;

/**
*
* @author Nicolae
* 
* Show historical data about sensors' state.
* Get entries by specified criteria, convert them into a JsonArray and send to client.
*/
@Path("/history")
public class SensorHistory {
	
	/*
	 * Get all entries from the database.
	 * Call like http://localhost:8080/RESTService/sensor/history/
	 * 
	 * return a JSONArray
	 */
	@GET
	@Produces("application/json")
	public Response getSensorData() throws JSONException {	
		ArrayList<Message> allData = DBQuery.getAllData();
		JsonArray jsonArray = JsonService.createJsonArray(allData);
		String result = "" + jsonArray;
		
		
		
		return Response.status(200).entity(result).build();
	}
	
	/*
	 * Get last n entries from the database.
	 * Call like http://localhost:8080/RESTService/sensor/history/2
	 * 
	 * @param limit the last n entries from the database
	 * return       a JSONArray
	 */
	@Path("{limit}")
	@GET
	@Produces("application/json")
	public Response getLastXEntries(@PathParam("limit") int limit) {
		ArrayList<Message> allData = DBQuery.getLastXEntries(limit);
		JsonArray jsonArray = JsonService.createJsonArray(allData);
		String result = "" + jsonArray;
		
		return Response.status(200).entity(result).build();
		
	}
	
	/*
	 * Get the maximum and minimum time that somebody spent in the room.
	 * Call like http://localhost:8080/RESTService/sensor/history/motion/2016-02-15
	 * 
	 * @param date calendar date to show the statistics
	 * return      a JSONArray
	 */
	@GET @Path("/motion/{date}")
	@Produces("application/json")    
    public Response getMinMaxTimeSomebodyInRoom(@PathParam("date") String date) {
		HashMap<String, Long> time = SensorHistoryCriteria.getMinMaxTimeSomebodyInRoom(date);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		
		for(Iterator<Map.Entry<String, Long>> it = time.entrySet().iterator(); it.hasNext(); ) {
		      Map.Entry<String, Long> entry = it.next();
		      jsonObject = new JSONObject();
		      jsonObject.put(entry.getKey(), entry.getValue());
		      jsonArray.put(jsonObject);
		    }
		
		String result = "" + jsonArray;
		
		return Response.status(200).entity(result).build();
	}
	
	/*
	 * Get the time spent in the room between two dates.
	 * Call like http://localhost:8080/RESTService/sensor/history/timespent/2016-02-15%2011:00:00&2016-02-15%2018:00:00
	 * 
	 * @param date_1 first calendar date indicating when to begin calculation
	 * @param date_2 second calendar date indicating when to stop calculation
	 * return      a JSONObject
	 */
	@GET @Path("/timespent/{date_1}&{date_2}")
	@Produces("application/json")    
    public Response getMinMaxTimeSomebodyInRoom(@PathParam("date_1") String date_1, @PathParam("date_2") String date_2) {
		long time = SensorHistoryCriteria.getTimeSpentInTheRoom(date_1, date_2);
		JSONObject jsonObject = new JSONObject();
        jsonObject.put("timeSpent", time);

		String result = "" + jsonObject;
		
		return Response.status(200).entity(result).build();
	}
	
	/*
	 * Get the maximum and the average value of the light in the room.
	 * Call like http://localhost:8080/RESTService/sensor/history/luminosity/2016-02-15
	 * 
	 * @param date calendar date to show the statistics
	 * return      a JSONArray
	 */
	@GET @Path("/luminosity/{date}")
	@Produces("application/json")    
    public Response getLuminosityStatistics(@PathParam("date") String date) {
		HashMap<String, Integer> time = SensorHistoryCriteria.getLuminosityStatistics(date);
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject;
		
		for(Iterator<Map.Entry<String, Integer>> it = time.entrySet().iterator(); it.hasNext(); ) {
		      Map.Entry<String, Integer> entry = it.next();
		      jsonObject = new JSONObject();
		      jsonObject.put(entry.getKey(), entry.getValue());
		      jsonArray.put(jsonObject);
		    }
		
		String result = "" + jsonArray;
		
		return Response.status(200).entity(result).build();
	}
}
