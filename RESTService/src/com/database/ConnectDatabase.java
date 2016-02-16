package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.settings.DatabaseSettings;
import com.udp.io.Log4j;

/**
 *
 * @author Nicolae
 */
public class ConnectDatabase {

	static Logger log = Log4j.initLog4j(ConnectDatabase.class);

	/**
	 * Connect to the database.
	 * 
	 * @return a opened connection to database
	 */
	public static Connection connectToDB() {
		Connection myConnection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			myConnection = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/" + DatabaseSettings.dbName + "?useSSL=false", 
					DatabaseSettings.dbUser,
					DatabaseSettings.dbPassword);

		} catch (ClassNotFoundException | SQLException e) {
			log.error("Exception in conectToBD() function, ConnectDatabase class : " + e.getMessage());
			e.printStackTrace();
		}

		return myConnection;
	}
}
