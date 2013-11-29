import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dbconnection.DBConnection;


public class Achievement {        
	public String name;        
	public String text;        
	public String image;        
	public String dateCreated;     
	
	public Achievement(String name, String description, String imageUrl, String dateCreated) {                
		this.name = name;                
		this.text = description;                
		this.image = imageUrl;                
		this.dateCreated = dateCreated;        
	}
	
	/**
	 * Given details of an achievement, add to achievements database.
	 * @param achievement name
	 * @param achievement description
	 * @param image url
	 */
	public static void addAchievement(String name, String description, String imageUrl) {                
		String query = String.format("INSERT into achievements (name, description, imageUrl) VALUES ('%s', '%s', '%s');", name, description, imageUrl);
		DBConnection.getInstance().executeQuery(query);         
	}
	
	/**
	 * Give an achievement to a user. 
	 * @param achievement
	 * @param user receiving achievement
	 */
    public static void giveAchievement(String achievement, String username) {                       
    	String stringDate = DBConnection.GetDate();                                         
    	String query = String.format("INSERT into userAchievements (username, achievement, dateCreated) VALUES ('%s', '%s', '%s');", username, achievement, stringDate);
    	DBConnection.getInstance().executeQuery(query);            
    }
    
	/**
	 * Get a list of achievements of a particular user. 
	 * @param username
	 * @return ArrayList of achievements for a user. 
	 */
    public static ArrayList<Achievement> getAchievementsFor(String user){
    	String query = String.format("SELECT achievements.name, achievements.description, achievements.imageUrl, filtered.dateCreated from achievements inner join (select * from userAchievements where username '%s') filtered on achievements.name = filtered.achievement", user);
		ArrayList<Achievement> achievements = new ArrayList<Achievement>();                  
		ResultSet rs = DBConnection.getInstance().executeQuery(query);                       
		try {
			while (rs.next()) {                                
				achievements.add(new Achievement(rs.getString("name"), rs.getString("description"), rs.getString("imageUrl"), rs.getString("dateCreated")));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return achievements;
    }
}