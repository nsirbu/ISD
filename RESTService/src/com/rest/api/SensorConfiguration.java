package com.rest.api;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

/**
 * Set the sensors' configurations to the configuration file config.properties 
 * @author Nicolae
 *
 */
@Path("/settings")
public class SensorConfiguration {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adjustSensorsThresholdValues(InputStream incomingSettings) {
		
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingSettings));
			String line = null;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		String receivedSettings = builder.toString();
		JSONObject jsonObject = new JSONObject(receivedSettings);
		System.out.println("LighThreshold " + jsonObject.getInt("lightThreshold"));
		System.out.println("Data Received: " + builder.toString());	
				
		// return HTTP response 200 in case of success
		return Response.status(200).entity(builder.toString()).build();		
	}
	
	@POST
	@Path("/heartbeat")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adjustHBThresholdValues(InputStream incomingSettings) {
		
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingSettings));
			String line = null;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		String receivedSettings = builder.toString();
		JSONObject jsonObject = new JSONObject(receivedSettings);
		System.out.println("LighThreshold " + jsonObject.getInt("lightThreshold"));
		System.out.println("Data Received: " + builder.toString());
				
		// return HTTP response 200 in case of success
		return Response.status(200).entity(builder.toString()).build();		
	}
}
