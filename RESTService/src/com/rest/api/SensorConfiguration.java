package com.rest.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.settings.ConfigurationsManager;
import com.udp.io.Log4j;

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
	 * @return 200 OK
	 */
	@POST
	@Path("/luminosity")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adjustSensorsThresholdValues(InputStream incomingSettings) {
		JSONObject jsonObject = new JSONObject(readIncommingMessage(incomingSettings));
		int lightThreshold = jsonObject.getInt("lightThreshold");
		configManager.setConfigValue("lightThreshold", lightThreshold + "");

		return Response.status(200).build();
	}

	/**
	 * Retrieve the heartbeat frequency value through REST API and set it in the
	 * configuration file on the server.
	 * 
	 * @param incomingSettings
	 *            the JSON message containing the heartbeat frequency value
	 * @return 200 OK
	 */
	@POST
	@Path("/heartbeat")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adjustHBThresholdValues(InputStream incomingSettings) {
		JSONObject jsonObject = new JSONObject(readIncommingMessage(incomingSettings));
		int HBThreshold = jsonObject.getInt("HBThreshold");
		configManager.setConfigValue("HBThreshold", HBThreshold + "");

		return Response.status(200).build();
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
}
