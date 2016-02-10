package isd;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONException;
import com.google.gson.JsonObject;
import com.udp.io.JsonService;
import com.udp.io.Log4j;

import isd.database.DBQuery;
import isd.model.Message;
 
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
	
//	static Logger log = Logger.getLogger(SensorCurrentData.class);

	@GET
	@Produces("application/json")
	public Response getSensorData() throws JSONException {
		
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		PropertyConfigurator.configure(classLoader.getResourceAsStream("log4j.properties") );
				
		Message message = DBQuery.getLastEntry();
		JsonObject jsonObject = JsonService.createJsonObject(message);
		String result = "" + jsonObject;
		
		return Response.status(200).entity(result).build();
	}
}