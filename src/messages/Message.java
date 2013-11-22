package messages;

import users.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import dbconnection.DBConnection;
import dbconnection.DBInfo;


/**
 * @class Message
 * This is a static class that should be used to manage the messaging and challenging
 * functionality between 2 users.
 */
public class Message{
	protected String text;
	protected int fromID;
	protected int toID;
	protected int quizID;
	protected int mType;  //0 for text, 1 for challenge, 2 for friend request
	protected int ID;
		
	public static Message createMessage(DBConnection connection, String text, int userIDFrom, int userIDTo, int type, int quizID){
		String sql = "INSERT INTO messages(fromUser, toUser, mType, message, quizID)" + " values ('"
				+ userIDFrom + "', '" + userIDTo + "', '" + type + "', '" + text + "', '" + quizID + "')";
		ResultSet rs = connection.executeQuery(sql);
		int ID = -1;
		try {
			if(rs.next()){
				ID = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Message m = null;
		
		if(type == 0){
			m = new Note(ID, userIDFrom, userIDTo, text);
		}
		
		else if(type == 1){
			m = new Challenge(ID, userIDFrom, userIDTo, text, quizID);
		}	
		else if(type == 2){
			//m = new FriendRequest(ID, userIDFrom, userIDTo, text, quizID);
		}
		return m;
	}
	
	public Message(int id, int userIDFrom, int userIDTo, String message){
		text = message;
		fromID = userIDFrom;
		toID = userIDTo;
		this.ID = id;
	}
	
	public void addToRecentActivity(){
		
	}
	
	/*
	 * Get all the messages TO the given user. 
	 * @param userIDTo
	 */
	public void addToRecieved(){
		//User userTo = getUser(toID);
		//List<Integer> messagesRecieved = userTo.getUserMessages();
		//messagesRecieved.add(0, ID);
		//userTo.setRecievedMessages(messagesRecieved);
	}
	
	public String getMessage(){
		return text;
	}
	
	public int getUserFrom(){
		return fromID;
	}
	
	public int getUserTo(){
		return toID;
	}
	
	public int getType(){
		return mType;
	}

	//public static void main(String[] args) {
	//	DBConnection conn = new DBConnection("C:/Users/Sohung/Desktop/Quizly/WebContent");
	//	Message myDB = createMessage(conn, "hello", 0, 1, 0, 2);
	//}

}