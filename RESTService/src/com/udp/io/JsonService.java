package com.udp.io;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import isd.model.Message;

public class JsonService {
	
	public static JsonArray createJsonArray(ArrayList<Message> data){
		Gson gson = new GsonBuilder().create();
		
		return gson.toJsonTree(data).getAsJsonArray();
	}
	
	public static JsonObject createJsonObject(Message message) {
		Gson gson = new Gson();
				
		return gson.toJsonTree(message).getAsJsonObject();		
	}
}