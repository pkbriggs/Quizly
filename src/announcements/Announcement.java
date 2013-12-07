package announcements;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import users.User;
import dbconnection.DBConnection;

public class Announcement {        
	public final int announcement_id;        
	public int userID;        
	public String posted;        
	public String subject;        
	public String body;  

	public Announcement(int announcement_id) {                
		this.announcement_id = announcement_id;                                
		try {                        
			String query = "SELECT * FROM announcements WHERE announcement_id = " + announcement_id;                 
			ResultSet r = DBConnection.getInstance().executeQuery(query); 
			if(!r.next()) throw new RuntimeException("Invalid announcement id!");                                                
			userID = r.getInt("userID");                        
			posted = r.getString("posted");                        
			subject = r.getString("subject");                        
			body = r.getString("body");                
		} catch (SQLException e) { 
			e.printStackTrace();
		}   
	}
	
	public static ArrayList<Announcement> getAnnouncements() {                                      
		String query = "SELECT * FROM announcements ORDER BY posted DESC";      
		ArrayList<Announcement> announces = new ArrayList<Announcement>(); 
		ResultSet r = DBConnection.getInstance().executeQuery(query);                     
		try {
			while (r.next()) {                                
				announces.add(new Announcement(r.getInt("userID")));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return announces;
	} 

	public static void deleteAnnouncement(int announcement_id) {                
		String query = "DELETE FROM announcements WHERE announcement_id = " + announcement_id;                
		DBConnection.getInstance().executeQuery(query);        
	}
	
	public static void newAnnouncement(User u, String subject, String body) {                
		String stringDate = DBConnection.GetDate(Calendar.getInstance().getTime());
		String query = String.format("INSERT INTO announcements (userID, posted, subject, body) VALUES ('%d', '%s', '%s');", u.getID(), stringDate, subject, body);                     
		DBConnection.getInstance().executeQuery(query);        
	}
	
	
}