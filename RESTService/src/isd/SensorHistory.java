package isd;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.google.gson.JsonArray;
import com.udp.io.JsonService;

import isd.database.DBQuery;
import isd.model.Message;

/**
*
* @author Nicolae
* 
* Show historical data about sensors' state.
* Get all entries from database, convert them into a JsonArray and send to client.
*/
@Path("/history")
public class SensorHistory {
	@GET
	@Produces("application/json")
	public Response getSensorData() throws JSONException {
		ArrayList<Message> allData = DBQuery.getAllData();
		JsonArray jsonArray = JsonService.createJsonArray(allData);
		String result = "" + jsonArray;
		
		return Response.status(200).entity(result).build();
	}
	
	@Path("{l}")
	@GET
	@Produces("application/json")
	public Response getLastXEntries(@PathParam("l") int limit) {
		ArrayList<Message> allData = DBQuery.getLastXEntries(limit);
		JsonArray jsonArray = JsonService.createJsonArray(allData);
		String result = "" + jsonArray;
		
		return Response.status(200).entity(result).build();
		
	}
}
