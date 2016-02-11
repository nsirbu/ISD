package com.udp.io;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.JsonObject;

import isd.model.Message;

/**
 * 
 * @author Nicolae
 *
 */
public class JsonServiceTest {
	
	@Test
	public void createJsonObjectShouldReturnJsonObject() {
		Message message = new Message(false, true, 111, "2020-11-30 00:00:00.0");
		JsonObject actualVersion = new JsonObject();
		actualVersion.addProperty("isHeartbeat", false);
		actualVersion.addProperty("timeReceived", "2020-11-30 00:00:00.0");
		actualVersion.addProperty("lightSensorVal", 111);
		actualVersion.addProperty("pirSensorVal", true);
		
		JsonObject expectedVersion = JsonService.createJsonObject(message);
		Assert.assertEquals(actualVersion, expectedVersion); 		
	}
}
