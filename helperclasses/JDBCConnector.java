package helperclasses;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCConnector {
	
	private Connection connection;
	
	public JDBCConnector() {
		try {
			// Load the JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			// Establish a connection
			connection = DriverManager.getConnection
					("jdbc:mysql://localhost/tiregroup?verifyServerCertificate=false&useSSL=true", "root", "csc");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Connection getConnection() {
		return connection;
	}

}
