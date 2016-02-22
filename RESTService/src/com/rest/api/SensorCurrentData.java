package com.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.database.DBQuery;
import com.google.gson.JsonObject;
import com.model.Message;
import com.settings.ConfigurationsManager;
import com.udp.io.JsonService;

/**
 *
 * @author Nicolae
 * 
 *         Show the last sensors' state. Get the last entry from the database,
 *         convert it to <code>JSONObject</code> and send to the client.
 */
@Path("/current")
public class SensorCurrentData {

	/**
	 * Get from the database the last entry, ie the last sensors' state.
	 * 
	 * @return a <code>JSONObject</code> containing the light sensor value, PIR
	 *         sensor value and the heartbeat value.
	 */
	@GET
	@Produces("application/json")
	public Response getLastSensorState() {
		Message message = DBQuery.getLastEntry();
		JsonObject jsonObject = JsonService.createJsonObject(message);
		String result = "" + jsonObject;

		return Response.status(200).entity(result).build();
	}

	/**
	 * Get the real time situation in the room. If current light value is lower
	 * than the light threshold then there is no one in the room. If current
	 * light value is bigger than the light threshold then there is someone in
	 * the room.
	 * 
	 * @return a <code>JSONObject</code> containing the yes or no values,
	 *         indicating if there is any motion or not
	 */
	@Path("/roomState")
	@GET
	@Produces("application/json")
	public Response getMotionState() {
		JSONObject jsonObject = new JSONObject();
		Message lastMessage = DBQuery.getLastEntry();
		ConfigurationsManager configManager = new ConfigurationsManager();
		int lightThreshold = Integer.parseInt(configManager.readConfigValue("lightThreshold"));

		if (lastMessage.getLightSensorVal() > lightThreshold) {
			jsonObject.put("isMotion", "yes");
		} else {
			jsonObject.put("isMotion", "no");
		}

		String result = "" + jsonObject;

		return Response.status(200).entity(result).build();
	}
}