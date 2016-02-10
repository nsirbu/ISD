package isd;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import isd.model.Message;
 
@Path("/data")
public class FtoCService {
 
	  @GET
	  @Produces("application/json")
	  public Response convertFtoC() throws JSONException {
		  JSONObject jsonObject = new JSONObject();
		  ArrayList<Message> allMessages = new ArrayList<Message>();
		  
		  try {
			  Class.forName("com.mysql.jdbc.Driver");
			  Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/isd", "root", "nicolae");
			 
			 String sql = "SELECT * FROM sensor_data";
			 PreparedStatement pst = con.prepareStatement(sql);
		     ResultSet rs = pst.executeQuery();
		            
		        if(rs.next()) {
			      while(rs.next()){
			    	  Message message = new Message();
			         message.setHeartbeat(rs.getInt("isHeartbeat") != 0); // Convert Int to Boolean
			         message.setTimeReceived(rs.getString("timeReceived"));
			         message.setLightSensorVal(Integer.parseInt(rs.getString("lightSensorVal")));
			         message.setPirSensorVal(Boolean.valueOf(rs.getString("pirSensorVal")));
				         
//			         jsonObject.put("timeReceived", timeReceived); 
//			         jsonObject.put("lightSensorVal", lightSensorVal); 
//			         jsonObject.put("pirSensorVal", pirSensorVal); 
//			         jsonObject.put("isHeartbeat", isHeartbeat); 
			      }			      
		        }
		        rs.close();
			 
			 con.close();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
 
		
		Double fahrenheit = 98.24;
		Double celsius;
		celsius = (fahrenheit - 32)*5/9; 
//		jsonObject.put("F Value", fahrenheit); 
//		jsonObject.put("C Value", celsius);
 
		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	  }
 
	  @Path("{f}")
	  @GET
	  @Produces("application/json")
	  public Response convertFtoCfromInput(@PathParam("f") float f) throws JSONException {
 
		JSONObject jsonObject = new JSONObject();
		float celsius;
		celsius =  (f - 32)*5/9; 
		jsonObject.put("F Value", f); 
		jsonObject.put("C Value", celsius);
 
		String result = "" + f + jsonObject;
		return Response.status(200).entity(result).build();
	  }
}