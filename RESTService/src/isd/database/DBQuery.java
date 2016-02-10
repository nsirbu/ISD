package isd.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import isd.model.Message;

public class DBQuery {

	public static ArrayList<Message> getAllData() {
		ArrayList<Message> dataFromDB = new ArrayList<Message>();

		try {
			Connection dbConnection = ConnectDatabase.ConectToBD();
			String sql = "SELECT * FROM sensor_data";
			PreparedStatement pst = dbConnection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				while (rs.next()) {
					Message message = new Message();
					message.setHeartbeat(rs.getInt("isHeartbeat") != 0); // Convert
																			// Int
																			// to
																			// Boolean
					message.setTimeReceived(rs.getString("timeReceived"));
					message.setLightSensorVal(Integer.parseInt(rs.getString("lightSensorVal")));
					message.setPirSensorVal(Boolean.valueOf(rs.getString("pirSensorVal")));

					dataFromDB.add(message);
				}
			}
			rs.close();
			dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return dataFromDB;
	}
}
