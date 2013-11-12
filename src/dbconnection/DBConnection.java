package dbconnection;

import java.io.*;
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
			SetUpDatabase();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("OpenConnection: Could not establish connection: " + e.getMessage());
		}
	}
	
	/**
	 * Closes the connection to the mysql database
	 */
	public void closeConnection(){
		try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes the query in the database and returns the result set
	 * @param query
	 * @return
	 */
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
	
	/**
	 * Sets up the database using the sql file SetUpDatabase.sql. 
	 * This will erase all data in the database, so only use for testing
	 * purposes.
	 * @return void
	 */
	private void SetUpDatabase(){
		try {
			System.out.println(new File("."));
			BufferedReader reader = new BufferedReader(new FileReader("SetUpDatabase.sql"));
			StringBuilder sb = new StringBuilder();
			String line = "";
			while(( line = reader.readLine() ) != null){
				sb.append(line);
			}
			
			java.sql.Statement stmt = connection.createStatement();
			stmt.executeQuery(sb.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}


}
