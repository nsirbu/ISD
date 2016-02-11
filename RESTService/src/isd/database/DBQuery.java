package isd.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import isd.model.Message;

/**
*
* @author Nicolae
*/
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
					message.setHeartbeat(rs.getInt("isHeartbeat") != 0); // Convert Int to Boolean
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
	
	public static Message getLastEntry(){
		Message lastMessage = new Message();
		try {
			Connection dbConnection = ConnectDatabase.ConectToBD();
			String sql = "SELECT * FROM sensor_data ORDER BY id DESC LIMIT 1";
			PreparedStatement pst = dbConnection.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();

			if (rs.next()) {
				while (rs.next()) {
					lastMessage.setHeartbeat(rs.getInt("isHeartbeat") != 0); // Convert Int to Boolean
					lastMessage.setTimeReceived(rs.getString("timeReceived"));
					lastMessage.setLightSensorVal(Integer.parseInt(rs.getString("lightSensorVal")));
					lastMessage.setPirSensorVal(Boolean.valueOf(rs.getString("pirSensorVal")));
				}
			}
			rs.close();
			dbConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lastMessage;
	}
	
	/**
	 * Records the given message object <code>Message</code></b> into
	 * the Database.
	 * @param receivedMessage - <code>Message</code>
	 * @return
	 */
	public static int recordMessage(Message receivedMessage){
		int affectedRows = 0;
		Connection dbConnection = ConnectDatabase.ConectToBD();
		try{
			PreparedStatement stmt = dbConnection.prepareStatement("INSERT INTO "
					+ "sensor_data (isHeartbeat, timeReceived, lightSensorVal, pirSensorVal) "
					+ "VALUES (?, ?, ?, ?)");
			
			stmt.setBoolean(1,receivedMessage.isHeartbeat());
			stmt.setString(2,receivedMessage.getTimeReceived());
			stmt.setInt(3,receivedMessage.getLightSensorVal());
			stmt.setBoolean(4,receivedMessage.getPirSensorVal());
			affectedRows = stmt.executeUpdate();
		}
		catch( Exception e ){ 
            e.printStackTrace();
            return 0;
		} 
       return affectedRows;
	}

}
