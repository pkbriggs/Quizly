package dbconnection;

import java.sql.*;
import java.util.ArrayList;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;


/**
 * Class: DBConnection
 * This class establishes a connection with the database. 
 */
public class DBConnection {

	private static String account= DBInfo.MYSQL_USERNAME;
	private static String password= DBInfo.MYSQL_PASSWORD;
	private static String database= DBInfo.MYSQL_DATABASE_NAME;
	private static String server= DBInfo.MYSQL_DATABASE_SERVER;

	private static java.sql.Connection connection;
	
	public DBConnection(){
		connection = null;
		OpenConnection();
	}
	
	private void OpenConnection(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			
			connection =  DriverManager.getConnection
					("jdbc:mysql://" + server, account ,password);
			java.sql.Statement stmt = connection.createStatement();
			stmt.executeQuery("USE " + database);
		
			/*This will create the database from scratch every time we open
			*the connection. To maintain database info over several sessions
			*simply comment it out.
			*/
			stmt.executeQuery("SOURCE SetUpDatabase.sql");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("OpenConnection: Could not establish connection: " + e.getMessage());
		}
	}
	
	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ResultSet executeQuery(String query){
		ResultSet rs= null;
		try {
			java.sql.Statement stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return rs;
	}
	


}
