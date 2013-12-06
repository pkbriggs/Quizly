package messages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import dbconnection.DBConnection;


/**
 * @class Message
 * This is a static class that should be used to manage the messaging and challenging
 * functionality between 2 users.
 */
public class Message {        
	public String username;        
	public String recipientName;        
	public String message;        
	public String title;        
	public String dateCreated;        
	
	public Message(String username, String recipientName, String message, String title, String dateCreated) {                
		this.username = username;                
		this.recipientName = recipientName;                
		this.message = message;                
		this.title = title;                
		this.dateCreated = dateCreated;        
	}

	/**
	 * Given details of a message, add to messages database.
	 * @param user sending message
	 * @param user receiving message
	 * @param text of message
	 * @param title of message
	 */
	public static void sendMessage(String username, String recipientName, String message, String title) {                            
		String stringDate = DBConnection.GetDate();
		String query = String.format("INSERT INTO messages (fromUser, toUser, message, title, dateCreated, seen) VALUES ('%s', '%s', '%s', '%s', '%s', '%d');", username, recipientName, message, title, stringDate, 0);
		System.out.println(query);
		DBConnection.getInstance().executeQuery(query);                
	}

	/**
	 * Return list of sent messages for a user
	 * @param username
	 * @param number of rows requested from the query (pass in 0 for all)
	 * @return list of message objects sent by a user
	 */
	public static ArrayList<Message> getSentMessagesFor(String username, int limit) {                
		String query = "SELECT * FROM messages WHERE fromUser = '" + username + "'";                
		if (limit > 0) {                        
			query += " LIMIT " + limit;                
		}                
		ArrayList<Message> messages = new ArrayList<Message>();                  
		ResultSet rs = DBConnection.getInstance().executeQuery(query);                       
		try {
			while (rs.next()) {                                
				messages.add(new Message(rs.getString("fromUser"), rs.getString("toUser"), rs.getString("message"), rs.getString("title"), rs.getString("dateCreated")));                        
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}                           
		return messages;        
	}
	
	/**
	 * Return a list of received messages for a user
	 * @param username
	 * @param number of rows requested from the query
	 * @return list of message object sent to a user (pass in 0 for all)
	 */
	public static ArrayList<Message> getMessagesFor(String username, int limit) {                
		String query = "SELECT * FROM messages WHERE toUser = '" + username + "'";                
		if (limit > 0) {                        
			query += " LIMIT " + limit;                
		}                
		ArrayList<Message> messages = new ArrayList<Message>();  
		ResultSet rs = DBConnection.getInstance().executeQuery(query);
		try {                                                
			while (rs.next()) {                                
				messages.add(new Message(rs.getString("fromUser"), rs.getString("toUser"), rs.getString("message"), rs.getString("title"), rs.getString("dateCreated")));                        
			}                
		} catch (SQLException e) {                        
			e.printStackTrace();                
		}                
		return messages;        
	}
	
	/**
	 * Return a conversation between two users. 
	 * @param user1
	 * @param user2 
	 * @return ArrayList of messages from user1 to user2
	 */
	public static ArrayList<Message> getMessagesFromTo(String From, String To, int limit){
		String query = String.format("SELECT * FROM messages WHERE toUser = '%s' AND fromUser = '%s'", To, From);
		if (limit > 0){
			query += " LIMIT " + limit;
		}
		ArrayList<Message> messages = new ArrayList<Message>();
		ResultSet rs = DBConnection.getInstance().executeQuery(query);
		try{
			while (rs.next()){
				messages.add(new Message(rs.getString("fromUser"), rs.getString("toUser"), rs.getString("message"), rs.getString("title"), rs.getString("dateCreated")));
			}
		} catch (SQLException e) {                        
			e.printStackTrace();                
		}              
		return messages;  
	}
	
	//returns 1 if seen, 0 if not seen
	public int getSeen(int messageID){
		String query = String.format("SELECT * FROM messages WHERE id = '%d'", messageID);
		ResultSet rs = DBConnection.getInstance().executeQuery(query);
		int seen = 0;
		try{
			while (rs.next()){
				seen = rs.getInt("seen");
			}
		} catch (SQLException e) {                        
			e.printStackTrace();                
		}              
		return seen;
	}
	
	public void setSeen(int messageID){
		String query = String.format("UPDATE messages SET seen = '%d' WHERE id = '%d'", 1, messageID);
		DBConnection.getInstance().executeQuery(query);
	}
}