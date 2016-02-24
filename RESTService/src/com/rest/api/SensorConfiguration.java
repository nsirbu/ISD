package com.rest.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.settings.ConfigurationsManager;
import com.udp.helper.Constants;
import com.udp.io.Log4j;
import com.udp.server.Server;

/**
 * 
 * @author Nicolae
 * 
 *         Set the sensors' configurations to the configuration file
 *         config.properties
 */
@Path("/settings")
public class SensorConfiguration {
	
	
	

	ConfigurationsManager configManager = new ConfigurationsManager();
	static Logger log = Log4j.initLog4j(SensorConfiguration.class);

	/**
	 * Retrieve the light threshold value through REST API and set it in the
	 * configuration file on the server. Using this threshold we can say if
	 * someone is in the room or not. If current light value is lower than the
	 * threshold then there is no one in the room. If current light value is
	 * bigger than the threshold then there is someone in the room.
	 * 
	 * @param incomingSettings
	 *            the <code>JSON</code> message containing the light threshold
	 *            value
	 * @return 200 OK in case of success and 400 Bad Request in case of failure 
	 */
	@POST
	@Path("/setLightThreshold")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adjustSensorsThresholdValues(InputStream incomingSettings) {
		JSONObject jsonObject = new JSONObject(readIncommingMessage(incomingSettings));
		int lightThreshold = -1;
		try {
			lightThreshold = jsonObject.getInt("lightThreshold");
		} catch (Exception e) {
			log.error("Exception in adjustSensorsThresholdValues() function, SensorConfiguration class : " + e.getMessage());
		}
		
		if (lightThreshold >= 0) {
			configManager.setConfigValue("lightThreshold", lightThreshold + "");
			return Response.status(200).build();
		} else {
			log.error("Wrong input data in adjustSensorsThresholdValues() method");
			return Response.status(400).build();
		}		
	}

	/**
	 * Retrieve the heartbeat frequency value through REST API and set it in the
	 * configuration file on the server.
	 * 
	 * @param incomingSettings
	 *            the JSON message containing the heartbeat frequency value
	 * @return 200 OK in case of success and 400 Bad Request in case of failure 
	 */
	@POST
	@Path("/setHBFrequency")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adjustHBThresholdValues(InputStream incomingSettings) {
		JSONObject jsonObject = new JSONObject(readIncommingMessage(incomingSettings));
		int HBFrequency = -1;
		try {
			HBFrequency = jsonObject.getInt("HBFrequency");
		} catch (Exception e) {
			log.error("Exception in adjustHBThresholdValues() function, SensorConfiguration class : " + e.getMessage());
		}
		
		if (HBFrequency >= 0) {
			configManager.setConfigValue("HBFrequency", HBFrequency + "");
			
			try {
				Server s = Server.getInstance();
				s.respondToClient(HBFrequency + "", InetAddress.getByName(Constants.ARDUINO_IP), Constants.ARDUINO_PORT);
			} catch (SocketException | UnknownHostException e) {
				log.error("Error sending configuration data to Arduino.");
			}			
			
			return Response.status(200).build();
		} else {
			log.error("Wrong input data in adjustSensorsThresholdValues() method");
			return Response.status(400).build();
		}
	}

	/**
	 * Retrieve the message sent through REST API and build a string from it.
	 * 
	 * @param incomingSettings
	 *            the retrieved message
	 * @return a <code>String</code> built from the input
	 */
	public String readIncommingMessage(InputStream incomingSettings) {
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingSettings));
			String line = null;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			log.error("Exception in readIncommingMessage() function, SensorConfiguration class : " + e.getMessage());
		}

		return builder.toString();
	}
	
	/**
	 * Get from configuration file the HB Frequency value.
	 * 
	 * @return a <code>JSONObject</code> containing the value of the threshold
	 */
	@GET @Path("/getHBFrequency")
	@Produces("application/json")
	public Response getHBFrequency() {
		ConfigurationsManager configManager = new ConfigurationsManager();
		JSONObject jsonObject = new JSONObject();
		int HBFrequency = 0;
		try {
			HBFrequency = Integer.parseInt(configManager.readConfigValue("HBFrequency"));
		} catch (Exception e) {
			log.error("Exception in getHBFrequency() function, SensorConfiguration class : " + e.getMessage());
		} finally {
			jsonObject.put("HBFrequency", HBFrequency);
		}
		
		String result = "" + jsonObject;

		return Response.status(200).entity(result).build();
	}
	
	/**
	 * Get from configuration file the light threshold value.
	 * 
	 * @return a <code>JSONObject</code> containing the value of the light threshold
	 */
	@GET @Path("/getLightThreshold")
	@Produces("application/json")
	public Response getLightThreshold() {
		ConfigurationsManager configManager = new ConfigurationsManager();
		JSONObject jsonObject = new JSONObject();
		int thresholdValue = 0;
		try {
			thresholdValue = Integer.parseInt(configManager.readConfigValue("lightThreshold"));
		} catch (Exception e) {
			log.error("Exception in getLightThreshold() function, SensorConfiguration class : " + e.getMessage());
		} finally {
			jsonObject.put("lightThreshold", thresholdValue);
		}
				
		String result = "" + jsonObject;

		return Response.status(200).entity(result).build();
	}
}
