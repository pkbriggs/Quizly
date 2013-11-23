package messages;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

	public static void sendMessage(DBConnection conn, String username, String recipientName, String message, String title) {                
		DateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");                
		Date date = new Date();                
		String stringDate = dateFormat.format(date);                
		String query = "INSERT into messages" + " (fromUser, toUser, message, title, dateCreated) VALUES ('" + 
		username + "', '" + recipientName + "', '" + message + "', '" + title + "', '" + stringDate + "')";                                   
		conn.executeQuery(query);                
	}

	public static ArrayList<Message> getSentMessagesFor(DBConnection conn, String username, int limit) {                
		String query = "SELECT * FROM messages" + " WHERE fromUser = '" + username + "'";                
		if (limit > 0) {                        
			query += " LIMIT " + limit;                
		}                
		ArrayList<Message> messages = new ArrayList<Message>();                  
		ResultSet rs = conn.executeQuery(query);                        
		try {
			while (rs.next()) {                                
				messages.add(new Message(rs.getString("fromUser"), rs.getString("toUser"), rs.getString("message"), rs.getString("title"), rs.getString("dateCreated")));                        
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}                           
		return messages;        
	}
	
	public static ArrayList<Message> getMessagesFor(DBConnection conn, String username, int limit) {                
		String query = "SELECT * FROM messages" + " WHERE toUser = '" + username + "'";                
		if (limit > 0) {                        
			query += " LIMIT " + limit;                
		}                
		ArrayList<Message> messages = new ArrayList<Message>();  
		ResultSet rs = conn.executeQuery(query);
		try {                                                
			while (rs.next()) {                                
				messages.add(new Message(rs.getString("fromUser"), rs.getString("toUser"), rs.getString("message"), rs.getString("title"), rs.getString("dateCreated")));                        
			}                
		} catch (SQLException e) {                        
			e.printStackTrace();                
		}                
		return messages;        
	}
	
	public static ArrayList<Message> getMessagesFromTo(DBConnection conn, String From, String To, int limit){
		String query = "SELECT * FROM messages" + " WHERE toUser = '" + To + "' AND fromUser = '" + From + "'";
		if (limit > 0){
			query += " LIMIT " + limit;
		}
		ArrayList<Message> messages = new ArrayList<Message>();
		ResultSet rs = conn.executeQuery(query);
		try{
			while (rs.next()){
				messages.add(new Message(rs.getString("fromUser"), rs.getString("toUser"), rs.getString("message"), rs.getString("title"), rs.getString("dateCreated")));
			}
		} catch (SQLException e) {                        
			e.printStackTrace();                
		}              
		return messages;  
	}
}