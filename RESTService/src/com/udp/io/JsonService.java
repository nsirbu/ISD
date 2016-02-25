package com.udp.io;

import java.util.ArrayList;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.model.Message;

/**
 * 
 * @author Nicolae
 *
 *         All methods concerning the JSON processing.
 *
 */
public class JsonService {

	/**
	 * Create <code>JsonArray</code> from a list with objects.
	 * 
	 * @param data
	 *            the list to transform in <code>JsonArray</code>
	 * @return the <code>JsonArray</code>
	 */
	public static JsonArray createJsonArray(ArrayList<Message> data) {
		Gson gson = new GsonBuilder().create();

		return gson.toJsonTree(data).getAsJsonArray();
	}

	/**
	 * Create <code>JsonObject</code> from a object <code>Message</code>.
	 * 
	 * @param message
	 *            the message to transform in <code>JsonObject</code>
	 * @return the <code>JsonObject</code>
	 */
	public static JsonObject createJSONObject(Message message) {
		Gson gson = new Gson();

		return gson.toJsonTree(message).getAsJsonObject();
	}
	
	/**
	 * Create <code>JSONObject</code> from the input data.
	 * 
	 * @param date
	 *            the value for the first pair of key-value
	 * @param key
	 *            the key for the second pair of key-value
	 * @param value
	 *            the value for the second pair of key-value
	 * @return a <code>JSONObject</code>
	 */
	public static JSONObject createJSONObject(String date, String key, long value) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("date", date);
		jsonObject.put(key, value);
		
		return jsonObject;
	}
}