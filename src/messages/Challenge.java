package messages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dbconnection.DBConnection;

/**
 * @class Challenge
 * This class interacts with the database to manage challenges.
 */
public class Challenge {        
	public int quizId;        
	public String username;        
	public String challengedUser;        
	public String quizName;        
	public int challengeId;       
	
	public Challenge(int challengeId, int quizId, String quizName, String username, String challengedUser) {                
		this.quizId = quizId;                
		this.username = username;                
		this.challengedUser = challengedUser;                
		this.quizName = quizName;                
		this.challengeId = challengeId;        
	} 
	
	/**
	 * Given details of a challenge, add to challenges database.
	 * @param username
	 * @param user being challenged
	 * @param quizId of challenged quiz
	 * @param name of challenged quiz
	 */
    public static void challengeUser(String username, String challengedUser, int quizId, String quizName) {                                             
    	String query = String.format("INSERT into challenges (username, challengedUser, quizId, quizName) values ('%s', '%s', '%d', '%s');", username, challengedUser, quizId, quizName);
    	DBConnection.getInstance().executeQuery(query);    
    }

	/**
	 * Given a username and a number of rows to limit the query to, get a user's challenges.
	 * @param username
	 * @param limit (pass in 0 to get all the user's challenges)
	 * @return ArrayList of challenge objects
	 */
    public static ArrayList<Challenge> getUserChallenges(String username, int limit) {                
    	String query = String.format("SELECT * from challenges where challengedUser = '%s';", username);                
    	if (limit > 0) {                        
    		query += " LIMIT " + limit;                
    	}                
    	ArrayList<Challenge> challenges = new ArrayList<Challenge>();                
    	try {                        
    		ResultSet rs = DBConnection.getInstance().executeQuery(query);                        
    		while (rs.next()) {                                
    			challenges.add(new Challenge(Integer.valueOf(rs.getString("id")), Integer.valueOf(rs.getString("quizId")), rs.getString("quizName"), rs.getString("username"), rs.getString("challengedUser")));                        
    		}                
    	} catch (SQLException e) {                        
    		e.printStackTrace();                
    	}                
    	return challenges;        
    }

	/**
	 * Indicate that a user has accepted a challenge: delete it from the table of challenges. 
	 * @param id of challenge
	 */
    public static void acceptChallenge(int challengeId) {                
    	String query = "DELETE from challenges where id = " + challengeId;                                
    	DBConnection.getInstance().executeQuery(query);             
    }
}