package isd.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.udp.io.Log4j;

import isd.model.Message;

/**
*
* @author Nicolae
* All methods concerning the access to the database.
*/
public class DBQuery {
	
	static Logger log = Log4j.initLog4j(DBQuery.class);
	
	public static ArrayList<Message> getAllData() {
		ArrayList<Message> dataFromDB = new ArrayList<Message>();

		try {
			Connection dbConnection = ConnectDatabase.ConectToBD();
			String sql = "SELECT * FROM sensor_data";
			PreparedStatement pst = dbConnection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Message message = new Message();
				message.setHeartbeat(rs.getInt("isHeartbeat") != 0); // Convert Int to Boolean
				message.setTimeReceived(rs.getString("timeReceived"));
				message.setLightSensorVal(Integer.parseInt(rs.getString("lightSensorVal")));
				message.setPirSensorVal(Boolean.valueOf(rs.getString("pirSensorVal")));

				dataFromDB.add(message);
			}
			
			rs.close();
			dbConnection.close();
		} catch (SQLException e) {
			log.error("Exception in getAllData() function, DBQuery class : " + e.getMessage());
			e.printStackTrace();
		}
		
		return dataFromDB;
	}
	
	public static Message getLastEntry(){
		Message lastMessage = new Message();
		try {
			Connection dbConnection = ConnectDatabase.ConectToBD();
			String sql = "SELECT * FROM sensor_data ORDER BY id DESC LIMIT 1";
			PreparedStatement pst = dbConnection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				lastMessage.setHeartbeat(rs.getInt("isHeartbeat") != 0); // Convert Int to Boolean
				lastMessage.setTimeReceived(rs.getString("timeReceived"));
				lastMessage.setLightSensorVal(Integer.parseInt(rs.getString("lightSensorVal")));
				lastMessage.setPirSensorVal(Boolean.valueOf(rs.getString("pirSensorVal")));
			}
			rs.close();
			dbConnection.close();
		} catch (SQLException e) {
			log.error("Exception in getLastEntry() function, DBQuery class : " + e.getMessage());
			e.printStackTrace();
		}
		
		return lastMessage;
	}

	//Comment.
}
