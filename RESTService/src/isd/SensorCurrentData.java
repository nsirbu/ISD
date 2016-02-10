package isd;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import com.google.gson.JsonObject;
import com.udp.io.JsonService;

import isd.database.DBQuery;
import isd.model.Message;
 
/**
*
* @author Nicolae
*/
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
}