package achievements;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

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
    	String stringDate = DBConnection.GetDate(Calendar.getInstance().getTime());                                         
    	String query = String.format("INSERT into userAchievements (username, achievement, dateCreated) VALUES ('%s', '%s', '%s');", username, achievement, stringDate);
    	DBConnection.getInstance().executeQuery(query);            
    }
    
	/**
	 * Get a list of achievements of a particular user. 
	 * @param username
	 * @return ArrayList of achievements for a user. 
	 */
    public static ArrayList<Achievement> getAchievementsFor(String user){
    	String query = String.format("SELECT achievements.name, achievements.description, achievements.imageUrl, filtered.dateCreated from achievements inner join (select * from userAchievements where username='%s') filtered on achievements.name = filtered.achievement", user);
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
    
    /**
     * Checks for achievements for that user and gives any if necessary
     */
    public static String CheckForCreatingQuizAchievements(String username){
    	String query = "SELECT * FROM quizzes WHERE creator='"+username+"'";
    	ResultSet r = DBConnection.getInstance().executeQuery(query);
    	try {
			r.last();
	    	int numRows = r.getRow();
    		
	    	if(numRows == 1){
	    		giveAchievement("Amateur Author", username); 
	    		return "Amateur Author";
	    	}
	    	
	    	if(numRows == 5){
	    		giveAchievement("Prolific Author", username); 
	    		return "Prolific Author";
	    	}
	    	
	    	if(numRows == 10){
	    		giveAchievement("Prodigious Author", username); 
	    		return "Prodigious Author";
	    	}
	    	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    
    public static String CheckForTakingQuizAchievements(String username, double score, int quizID, boolean practice_mode){
    	String query = "SELECT * FROM scores WHERE username='"+username+"'";
    	ResultSet r = DBConnection.getInstance().executeQuery(query);
    	String achievements = "";
    	boolean previous_achievement = false;
    	
    	if(practice_mode){
    		Achievement.giveAchievement("Practice Makes Perfect", username);
    		achievements += "Practice Makes Perfect";
    		return achievements;
    	}
    	
    	try {
			r.last();
	    	int numRows = r.getRow();
    		
	    	if(numRows == 10){
	    		giveAchievement("Quiz Machine", username);
	    		if(previous_achievement){
	    			achievements += " , ";
	    		}
	    		achievements += "Quiz Machine";
	    		previous_achievement = true;

	    	}
	    	
	    	query = "SELECT * FROM scores WHERE quizID='"+quizID+"' ORDER BY score";
	    	r = DBConnection.getInstance().executeQuery(query);
	    	if(r == null)
	    		return achievements;

	    	double top_score = 0.0;
	    	if(r != null){
	    		r.next();
	    		top_score = r.getDouble("score");
	    	}
	    	
	    	if(score > top_score){
	    		giveAchievement("I Am The Greatest", username);
	    		if(previous_achievement){
	    			achievements += " , ";
	    		}
	    		achievements += "I Am The Greatest";
	    	}
	    	
	    	return achievements;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return null;
    }
}