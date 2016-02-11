package isd.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

/**
*
* @author Nicolae
*/
public class ConnectDatabase {
	public static Connection ConectToBD() {
		Connection myConnection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/isd", "root",
					"12345");

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return myConnection;
	}
}
