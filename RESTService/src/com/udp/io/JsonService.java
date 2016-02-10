package com.udp.io;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import isd.model.Message;

public class JsonService {
	
public static JsonArray createJSArray(ArrayList<Message> data){
		
		JsonArray jsonData = null;
		Gson gson = new GsonBuilder().create();
		return jsonData = gson.toJsonTree(data).getAsJsonArray();
	}

}
