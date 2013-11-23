package messages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dbconnection.DBConnection;
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
	
    public static void challengeUser(DBConnection conn, String username, String challengedUser, int quizId, String quizName) {                
    	String query = "INSERT into challenges (username, challengedUser, quizId, quizName) values ('" + username + "', '" + challengedUser + "', '" + quizId + "', '" + quizName + "')";                                 
    	conn.executeQuery(query);    
    }
    
    public static ArrayList<Challenge> getChallengesForUser(DBConnection conn, String username, int limit) {                
    	String query = "select * from challenges where challengedUser = '" + username + "'";                
    	if (limit > 0) {                        
    		query += " LIMIT " + limit;                
    	}                
    	ArrayList<Challenge> challenges = new ArrayList<Challenge>();                
    	try {                        
    		ResultSet rs = conn.executeQuery(query);                        
    		while (rs.next()) {                                
    			challenges.add(new Challenge(Integer.valueOf(rs.getString("challengeId")), Integer.valueOf(rs.getString("quizId")), rs.getString("quizName"), rs.getString("username"), rs.getString("challengedUser")));                        
    		}                
    	} catch (SQLException e) {                        
    		e.printStackTrace();                
    	}                
    	return challenges;        
    }

    public static void acceptChallenge(DBConnection conn, String username, int challengeId) {                
    	String query = "delete from challenges where challengeId = " + challengeId;                                
    	conn.executeQuery(query);                
    }
}