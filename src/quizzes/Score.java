package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dbconnection.DBConnection;

public class Score {
	
	private String user;
    private int quizID;
    private long time;
    private double score;
    private String dateTaken;
    
    public static ArrayList<Score>getScores(String query){
    	System.out.println("SCORES : returning scores: ");
    	ArrayList<Score> scores = new ArrayList<Score>();
		DBConnection connection = DBConnection.getInstance();
		ResultSet rs = connection.executeQuery(query);
		
		try {
			int i = 0;
			while(rs.next()){
		    	System.out.println("SCORES : this is the index:  "+ i);

				Score score = new Score();
				score.quizID = rs.getInt("quizID");
				score.user = rs.getString("username");
				score.time = rs.getLong("time");
				score.score = rs.getDouble("score");
				score.dateTaken = rs.getString("dateTaken");
				
				scores.add(score);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return scores;
    }
    
    public int getQuizID(){
    	return quizID;
    }
    
    public double getScore(){
    	return score;
    }
    
    public String dateTaken(){
    	return dateTaken;
    }
    
    public String getUsername(){
    	return user;
    }
    
    public long getTime(){
    	return time;
    }
}
