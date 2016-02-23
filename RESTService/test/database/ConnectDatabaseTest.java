package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author Nicolae
 *
 *         Test case for database connection.
 */
public class ConnectDatabaseTest {

	@Test
	public void conectToBDShouldReturnAOpenedConnectionToDB() {

		Connection myConnection = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			myConnection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/isd", "root",
					"nicolae");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		Assert.assertEquals(true, !myConnection.equals(null));
	}
}
