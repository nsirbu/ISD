package com.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.model.Message;
import com.udp.io.Log4j;

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
			
			pst.close();
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
	 * Get all entries from the database.
	 * 
	 * @return a list with objects of <code>Message</code> class.
	 */
	public static ArrayList<Message> getAllDataByParameter(String date) {
		String sqlQuery = "SELECT * FROM sensor_data where DATE(timeReceived)='" + date + "'";
		
		return getDataSet(sqlQuery);
	}
	
	/**
	 * Get all entries from the database.
	 * 
	 * @return a list with objects of <code>Message</code> class.
	 */
	public static ArrayList<Message> getAllDataBetweenTwoTimeValues(String date_1, String date_2) {
		String sqlQuery = "SELECT * FROM sensor_data where timeReceived BETWEEN '" + date_1 + "' AND '" + date_2 + "'";
		
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
			pst.close();
			rs.close();
			dbConnection.close();
		} catch (SQLException e) {
			log.error("Exception in getDataSet() function, DBQuery class : " + e.getMessage());
			e.printStackTrace();
		}
		return dataFromDB;	
	}
	
	/**
	 * Records the given message object <code>Message</code></b> into
	 * the Database.
	 * @param receivedMessage - <code>Message</code>
	 * @return <code>Integer</code> - number of rows affected. 
	 * If the method returns 0 - the query wasn't successful. 
	 */
	public static int recordMessage(Message receivedMessage){
		int affectedRows = 0;
		Connection dbConnection = ConnectDatabase.connectToDB();
		try{
			PreparedStatement stmt = dbConnection.prepareStatement("INSERT INTO "
					+ "sensor_data (isHeartbeat, timeReceived, lightSensorVal, pirSensorVal) "
					+ "VALUES (?, ?, ?, ?)");
			
			stmt.setBoolean(1,receivedMessage.isHeartbeat());
			stmt.setString(2,receivedMessage.getTimeReceived());
			stmt.setInt(3,receivedMessage.getLightSensorVal());
			stmt.setBoolean(4,receivedMessage.getPirSensorVal());
			affectedRows = stmt.executeUpdate();
			stmt.close();
			dbConnection.close();
		}
		catch( Exception e ){ 
			log.error("Exception in recordMessage() function, DBQuery class : " + e.getMessage());
            e.printStackTrace();
            return 0;
		} 
       return affectedRows;
	}
	
	/**
	 * Get the total number of motion detection during a day. 
	 * 
	 * @param date         the day for which to determine
	 * @param pirSensorVal the state of the sensor that interests us, true or false
	 * @return             how many motions was detected
	 */
	public static int getMotionActivityForDay(String date, boolean pirSensorVal) {
		String sqlQuery = "SELECT COUNT(*) FROM sensor_data where DATE(timeReceived)='" + date + "' and pirSensorVal='"
				+ pirSensorVal + "' and isHeartbeat='false'";
		int count = 0;

		try {
			Connection dbConnection = ConnectDatabase.connectToDB();
			PreparedStatement pst = dbConnection.prepareStatement(sqlQuery);
			ResultSet rs = pst.executeQuery();

			while (rs.next()) {
				count = rs.getInt(1);
			}
			pst.close();
			rs.close();
			dbConnection.close();
		} catch (SQLException e) {
			log.error("Exception in getMotionActivityForDay() function, DBQuery class : " + e.getMessage());
			e.printStackTrace();
		}

		return count;
	}
}
