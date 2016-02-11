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
* 
* All methods concerning the access to the database.
*/
public class DBQuery {
	
	static Logger log = Log4j.initLog4j(DBQuery.class);
	
	/**
	 * Get last entry from the database.
	 * 
	 * @return a instance of class <code>Message</code>
	 */
	public static Message getLastEntry(){
		Message lastMessage = new Message();
		try {
			Connection dbConnection = ConnectDatabase.connectToDB();
			String sql = "SELECT * FROM sensor_data ORDER BY id DESC LIMIT 1";
			PreparedStatement pst = dbConnection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				lastMessage.setHeartbeat(rs.getBoolean("isHeartbeat"));
				lastMessage.setTimeReceived(rs.getString("timeReceived"));
				lastMessage.setLightSensorVal(rs.getInt("lightSensorVal"));
				lastMessage.setPirSensorVal(rs.getBoolean("pirSensorVal"));
			}
			
			rs.close();
			dbConnection.close();
		} catch (SQLException e) {
			log.error("Exception in getLastEntry() function, DBQuery class : " + e.getMessage());
			e.printStackTrace();
		}
		
		return lastMessage;
	}
	
	/**
	 * Get all entries from the database.
	 * 
	 * @return a list with objects of <code>Message</code> class.
	 */
	public static ArrayList<Message> getAllData() {
		String sqlQuery = "SELECT * FROM sensor_data";
		
		return getDataSet(sqlQuery);
	}
	
	/**
	 * Get the last x elements from database.
	 * 
	 * @param  x number of elements to retrieve from the database
	 * @return   a list with objects of <code>Message</code> class
	 */
	public static ArrayList<Message> getLastXEntries(int x) {
		String sqlQuery = "SELECT * FROM sensor_data ORDER BY id DESC LIMIT " + x;
		
		return getDataSet(sqlQuery);	
	}
	
	/**
	 * Get a list of objects from database according to specified query.
	 * 
	 * @param sqlQuery query to be executed
	 * @return         a list with objects of <code>Message</code> class
	 */
	public static ArrayList<Message> getDataSet(String sqlQuery) {
		ArrayList<Message> dataFromDB = new ArrayList<Message>();

		try {
			Connection dbConnection = ConnectDatabase.connectToDB();
			PreparedStatement pst = dbConnection.prepareStatement(sqlQuery);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				Message message = new Message();
				message.setHeartbeat(rs.getBoolean("isHeartbeat"));
				message.setTimeReceived(rs.getString("timeReceived"));
				message.setLightSensorVal(rs.getInt("lightSensorVal"));
				message.setPirSensorVal(rs.getBoolean("pirSensorVal"));

				dataFromDB.add(message);
			}
			rs.close();
			dbConnection.close();
		} catch (SQLException e) {
			log.error("Exception in getDataSet() function, DBQuery class : " + e.getMessage());
			e.printStackTrace();
		}
		return dataFromDB;	
	}	
}
