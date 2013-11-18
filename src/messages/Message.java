package messages;

import users.*;
import java.sql.ResultSet;
import java.sql.SQLException;


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
	protected int mType;  //0 for text, 1 for challenge (maybe 2 for friend request)
	
	
	public static Message createMessage(String text, int userIDFrom, int userIDTo, int type, int quizID){
		String sql = "INSERT into messages (fromUser, toUser, mType, message, quizID)" + " VALUES ('"
				+ userIDFrom + "', '" + userIDTo + "', '" + type + "', '" + text + "', '" + quizID + "')";
		Message m = null;
		
		if(type == 0){
			m = new Note(userIDFrom, userIDTo, text);
		}
		
		else if(type == 1){
			m = new Challenge(userIDFrom, userIDTo, text, quizID);
		}
		return m;
	}
	
	public Message(int userIDFrom, int userIDTo, String message){
		text = message;
		fromID = userIDFrom;
		toID = userIDTo;
	}
	
	public void addToRecentActivity(){
		
	}
	
	public void addToRecieved(){
		
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
	
}