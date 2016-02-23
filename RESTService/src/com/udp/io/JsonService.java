package com.udp.io;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.model.Message;

/**
 * 
 * @author Nicolae
 *
 * All methods concerning 
 *
 */
public class JsonService {
	
	public static JsonArray createJsonArray(ArrayList<Message> data){
		Gson gson = new GsonBuilder().create();
		
		return gson.toJsonTree(data).getAsJsonArray();
	}
	
	public static JsonObject createJSONObject(Message message) {
		Gson gson = new Gson();
				
		return gson.toJsonTree(message).getAsJsonObject();		
	}
}