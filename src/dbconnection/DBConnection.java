package dbconnection;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.ResultSetMetaData;
import com.mysql.jdbc.Statement;


/**
 * Class: DBConnection
 * This class establishes a connection with the database. 
 */
public class DBConnection {
	
	private static DBConnection instance = null;
	private static String rootDirectory = null; // this is initialized in contextInitialized

	private static String account= DBInfo.MYSQL_USERNAME;
	private static String password= DBInfo.MYSQL_PASSWORD;
	private static String database= DBInfo.MYSQL_DATABASE_NAME;
	private static String server= DBInfo.MYSQL_DATABASE_SERVER;

	private static java.sql.Connection connection;
	private String rootPath;
	
	private DBConnection(String rootPath){
		connection = null;
		this.rootPath = rootPath;
		OpenConnection();
	}
	
	public static DBConnection getInstance() {
		if (instance == null)
			instance = new DBConnection(DBConnection.rootDirectory);
		return instance;
	}
	
	public static void setRootDirectory(String root) {
		DBConnection.rootDirectory = root;
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
			// SetUpDatabase();
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
			// this is the trick -- you need to pass different SQL to different methods
			if (query.startsWith("SELECT")) {
				rs = stmt.executeQuery(query);
			} else if (query.startsWith("UPDATE") || query.startsWith("INSERT")
					|| query.startsWith("DELETE")) {
				stmt.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
				rs = stmt.getGeneratedKeys();
			} else {
				stmt.execute(query, Statement.RETURN_GENERATED_KEYS);
				rs = stmt.getGeneratedKeys();
			}
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
		// http://stackoverflow.com/questions/8437240/how-can-i-run-a-script-file-using-the-source-command
		try {
			java.sql.Statement stm = connection.createStatement();
			// http://stackoverflow.com/questions/15506341/where-to-place-a-directory-of-static-files-in-eclipse-dynamic-web-project
			BufferedReader reader = new BufferedReader(new FileReader(rootPath+"/static/SetUpDatabase.sql"));
			
			String line;
			while ((line = reader.readLine()) != null) {

				while(!line.endsWith(";")){
					line += reader.readLine();
				}
				
				System.out.println(line);
				if (line == null) {
					break;
				}
				if (line.trim().length() == 0) {
					continue;
				}
				System.out.println(line);
				
				this.executeQuery(line);

			}
			stm.close();
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	/**
	 * Helper method for getting a session attribute given a request
	 * @param request
	 * @param attribute
	 * @return
	 */
	public static Object GetSessionAttribute(HttpServletRequest request, String attribute){
		HttpSession session = request.getSession();
		return session.getAttribute(attribute);
	}

	/**
	 * Helper method that Returns the current DBConnection stored in the Servlet Context
	 * @param request
	 * @return
	 */
	public static DBConnection GetConnection(HttpServletRequest request){
		ServletContext context = request.getServletContext();
		return (DBConnection) context.getAttribute("dbconnection");
	}
	
	/**
	 * Returns the current date in the form "yyyy-MM-dd HH:mm:ss"
	 * Useful for getting a date string to place in database
	 * @return
	 */
	public static String GetDate(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = Calendar.getInstance().getTime();		
		return dateFormat.format(currentTime);
	}
	

}
