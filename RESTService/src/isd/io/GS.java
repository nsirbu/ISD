package isd.io;

import java.util.ArrayList;

import isd.model.Message;

public class GS {
	
	public static JsonArray createJSArray(ArrayList<Message> data){
		
		JsonArray jsonData = null;
		Gson gson = new GsonBuilder().create();
		return jsonData = gson.toJsonTree(data).getAsJsonArray();
	}

}
