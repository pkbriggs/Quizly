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
    private Date dateTaken;
    
    public static ArrayList<Score>getScores(String query){
    	ArrayList<Score> scores = new ArrayList<Score>();
		DBConnection connection = DBConnection.getInstance();
		ResultSet rs = connection.executeQuery(query);
		
		try {
			while(rs.next()){
				Score score = new Score();
				score.quizID = rs.getInt("quizID");
				score.user = rs.getString("username");
				score.time = rs.getLong("time");
				score.score = rs.getDouble("score");
				score.dateTaken = rs.getDate("dateTaken");
				
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
    
    public Date dateTaken(){
    	return dateTaken;
    }
    
    public String getUsername(){
    	return user;
    }
    
    public long getTime(){
    	return time;
    }
}
