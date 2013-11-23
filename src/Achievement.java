import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dbconnection.DBConnection;




public class Achievement {        
	public String name;        
	public String description;        
	public String imageUrl;        
	public String dateCreated;        
	public Achievement(String name, String description, String imageUrl, String dateCreated) {                
		this.name = name;                
		this.description = description;                
		this.imageUrl = imageUrl;                
		this.dateCreated = dateCreated;        
	}
	
	public static void addAchievement(DBConnection conn, String name, String description, String imageUrl) {                
		String query = "INSERT into achievements" + " (name, description, imageUrl) VALUES ('" + name + "', '" + description + "', '" + imageUrl + "')";                                
		conn.executeQuery(query);                

	}
	
    public static void awardAchievement(DBConnection conn, String name, String username) {                
    	DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");                
    	Date date = new Date();                
    	String stringDate = dateFormat.format(date);                
    	String query = "INSERT into achievements" + " (username, achievementName, dateCreated) VALUES ('" + username + "', '" + name + "', '" + stringDate + "')";                             
    		conn.executeQuery(query);                
    }
}