package com.rest.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import com.database.DBQuery;
import com.google.gson.JsonObject;
import com.model.Message;
import com.udp.io.JsonService;
import com.udp.io.Log4j;

/**
 *
 * @author Nicolae
 * 
 *         Show the current sensors' state analyzing data from the database.
 */
@Path("/current")
public class SensorCurrentData {

	static Logger log = Log4j.initLog4j(SensorCurrentData.class);

	/**
	 * Get from the database the last entry, ie the last sensors' state.
	 * 
	 * @return a <code>JSONObject</code> containing the light sensor value, PIR
	 *         sensor value and the heartbeat value.
	 */
	@GET
	@Produces("application/json")
	public Response getLastSensorState() {
		Message message = new Message();
		try {
			message = DBQuery.getLastEntry();
			JsonObject jsonObject = JsonService.createJSONObject(message);
			String result = "" + jsonObject;

			return Response.status(200).entity(result).build();
		} catch (Exception e) {
			log.error("Exception in getLastSensorState() function, SensorCurrentData class : " + e.getMessage());

			return Response.status(500).build();
		}
	}

	/**
	 * Get the real time situation in the room, if there is someone and if the
	 * light is turned on or off.
	 * 
	 * @return a <code>JSONObject</code> containing the <code>yes</code> or
	 *         <code>no</code> values
	 */
	@Path("/presence")
	@GET
	@Produces("application/json")
	public Response getRoomState() {
		String result = "" + SensorHistoryCriteria.checkForSomeoneInTheRoom();

		return Response.status(200).entity(result).build();

	}
}