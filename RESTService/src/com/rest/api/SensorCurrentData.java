package com.rest.api;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONException;

import com.database.DBQuery;
import com.google.gson.JsonObject;
import com.model.Message;
import com.udp.io.JsonService;
import com.udp.io.Log4j;
 
/**
*
* @author Nicolae
* 
* Show the last sensors' state.
* Get the last entry from the database, convert it to JsonObject and send to client. 
*/
@Path("/current")
public class SensorCurrentData {
	
	Logger log = Log4j.initLog4j(SensorCurrentData.class);

	@GET
	@Produces("application/json")
	public Response getSensorData() throws JSONException {			
		Message message = DBQuery.getLastEntry();
		JsonObject jsonObject = JsonService.createJsonObject(message);
		String result = "" + jsonObject;
		
		return Response.status(200).entity(result).build();
	}
}