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
	 * Create a <code>JSONObject</code> from <code>String</code>.
	 * 
	 * @param startDateInterval
	 *            the value for the first key
	 * @param totalTime
	 *            the value for the second key
	 * @return a <code>JSONObject</code>
	 */
	public static JSONObject createJSONWithLightOnStatisticsDataDuringDay(String startDateInterval, String totalTime) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("date", startDateInterval);
		jsonObject.put("totalTime", totalTime);

		return jsonObject;
	}
}