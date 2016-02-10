package isd;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;
import com.udp.io.JsonService;

import isd.database.DBQuery;
import isd.model.Message;
 
@Path("/current")
public class SensorCurrentData {

	@GET
	@Produces("application/json")
	public Response getSensorData() throws JSONException {
		Message message = DBQuery.getLastEntry();
		JsonObject jsonObject = JsonService.createJsonObject(message);
		String result = "" + jsonObject;
		
		return Response.status(200).entity(result).build();
	}
 
	@Path("{f}")
	@GET
	@Produces("application/json")
	public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		float celsius;
		celsius = (f - 32) * 5 / 9;
		jsonObject.put("F Value", f);
		jsonObject.put("C Value", celsius);

		String result = "" + f + jsonObject;
		return Response.status(200).entity(result).build();
	}
}