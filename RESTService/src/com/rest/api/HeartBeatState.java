package com.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.database.DBQuery;
import com.model.Message;
import com.google.gson.JsonObject;
import com.udp.server.HeartBeatJob;

@Path("/")
public class HeartBeatState {
	
	/**
	 * Checks for the last message time and compares it to the minimal 
	 * frequency rate.<br><b>Request format:</b> <code>http://localhost:8080/RESTService/hbstate</code>
	 * 
	 * @return a JSONArray containing the heart-beat state (late, OK) 
	 * 						and the last time a message has been received
	 */
	@GET
	@Path("/hbstate")
	@Produces("application/json")
	public Response getHeartBeatState() {
		try {
			Message lastMsg = DBQuery.getLastEntry();
			HeartBeatJob heartBeatService = new HeartBeatJob();
			boolean status = heartBeatService.isHeartBeatOnTime();
			JsonObject hrtBtStateJson = new JsonObject(); // Heart-beat message state
			hrtBtStateJson.addProperty("status", status);
			hrtBtStateJson.addProperty("lastMessageTime", lastMsg.getTimeReceived());
			hrtBtStateJson.addProperty("pir_sensor_value", lastMsg.getPirSensorVal());
			hrtBtStateJson.addProperty("light_level", lastMsg.getLightSensorVal());
			String result = hrtBtStateJson.toString();
			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			
			return Response.status(500).entity(e.getMessage()).build();
		}
		
	}
}
