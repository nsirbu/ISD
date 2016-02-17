package com.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.settings.ConfigurationsManager;
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
		ConfigurationsManager configReader = new ConfigurationsManager();
		String dbUser = configReader.readConfigValue("dbUser");
		String dbPassword = configReader.readConfigValue("dbPassword");
		String dbName = configReader.readConfigValue("dbName");
		Connection myConnection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			myConnection = (Connection) DriverManager
					.getConnection("jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false", dbUser, dbPassword);

		} catch (ClassNotFoundException | SQLException e) {
			log.error("Exception in conectToBD() function, ConnectDatabase class : " + e.getMessage());
			e.printStackTrace();
		}

		return myConnection;
	}
}
