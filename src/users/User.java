package users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import quizzes.Quiz;

import users.Friendship.FriendshipStatus;
import dbconnection.DBConnection;

/**
 * @class User
 * This will be a static class will be stored as a context attribute 
 * that will interact with the database to get all the information related
 * to users
 */
public class User {
	
	private int id;
	private String username;
	private String photoFilename;
	
	
	public User(int id, String username, String photoFilename) {
		this.id = id;
		this.username = username;
		this.photoFilename = photoFilename;
	}
	
	public int getID() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPhotoFilename() {
		return photoFilename;
	}
	
	/**
	 * Given a @userID, will compare the hashed @pass to the hashed password associated with the user's account.
	 * @param userID
	 * @param pass the plain-text password
	 * @return true if the password is correct, false otherwise
	 */
	public static boolean checkPassword(int userID, String pass) {
		String sql = String.format("SELECT passwordhash FROM users WHERE id = %d;", userID);
		return checkPasswordHelper(sql, pass);
	}
	
	/**
	 * Given the current user's @session, will determine if the user is currently logged in.
	 * @param session
	 * @return true if the user is logged in, false otherwise
	 */
	public static boolean isLoggedIn(HttpSession session) {
		if (session.getAttribute("loggedin") == null || (Boolean)session.getAttribute("loggedin") == false)
			return false;
		else
			return true;
	}
	
	/**
	 * Given the current user's @session, will return their username if they are logged in. Otherwise, null is returned.
	 * @param session
	 * @return their username if they are logged in, null otherwise
	 */
	public static String getUsername(HttpSession session) {
		if (User.isLoggedIn(session))
			return (String) session.getAttribute("username");
		else
			return null;
	}
	
	public static int getID(HttpSession session) {
		if (User.isLoggedIn(session))
			return (Integer) session.getAttribute("userid");
		else
			return -1;
		
	}
	
	public static String getUsernameFromID(int id) {
		String sql = String.format("SELECT username FROM users WHERE id = '%d';", id);
		
		ResultSet results = DBConnection.getInstance().executeQuery(sql);
		
		try {
			if (results.next()) {
				return results.getString("username"); 
			} else {
				// this happens if there are no users with the specified ID, the function simply returns false
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static int getIDFromUsername(String username) {
		String sql = String.format("SELECT id FROM users WHERE username = '%s';", username);
		
		ResultSet results = DBConnection.getInstance().executeQuery(sql);
		
		try {
			if (results.next()) {
				return results.getInt("id"); 
			} else {
				// this happens if there are no users with the specified ID, the function simply returns false
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public static User getUserFromID(int id) {
		String sql = String.format("SELECT * FROM users WHERE id = '%d';", id);
		
		ResultSet results = DBConnection.getInstance().executeQuery(sql);
		
		try {
			if (results.next()) {
				String username = results.getString("username");
				String photoFilename = results.getString("picturefile");
				User user = new User(id, username, photoFilename);
				return user;
			} else {
				// this happens if there are no users with the specified ID, the function simply returns false
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Given a @username, will compare the hashed @pass to the hashed password associated with the user's account.
	 * @param userID
	 * @param pass the plain-text password
	 * @return true if the password is correct, false otherwise
	 */
	public static boolean checkPassword(String username, String pass) {
		String sql = String.format("SELECT passwordhash FROM users WHERE username = '%s';", username);
		return checkPasswordHelper(sql, pass);
	}
	
	/**
	 * Helper method for checking a user's password - 
	 * Will compare the hashed @pass to the hashed password associated with the user's account.
	 * @param sql the SQL string specifying the query
	 * @param pass the plain-text password
	 * @return true if the password is correct, false otherwise
	 */
	private static boolean checkPasswordHelper(String sql, String pass) {
		ResultSet results = DBConnection.getInstance().executeQuery(sql);
		
		try {
			if (results.next()) {
				String correctHash = results.getString("passwordhash"); 
				String hashedPass = generatePasswordHash(pass);
				if (hashedPass.equals(correctHash)) 
					return true;
			} else {
				// this happens if there are no users with the specified ID, the function simple returns false
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Given a @username and @pass, creates a user account and stores it in the database.
	 * The plain-text password is not stored, only the SHA-hashed password.
	 * @param username
	 * @param pass
	 */
	public static void createUser(String username, String pass) {
		String check = String.format("SELECT * FROM users WHERE username = '%s';", username);
		ResultSet results = DBConnection.getInstance().executeQuery(check);
		if (results != null) {
			String hashedPass = generatePasswordHash(pass);
			String sql = String.format("INSERT INTO users (username, passwordhash) VALUES ('%s', '%s');", username, hashedPass);
			DBConnection.getInstance().executeQuery(sql);
		}
		
		
	}
	
	/**
	 * Will set the profile picture of @userID to the picture located
	 * at @url.
	 * TODO: Consider allowing file uploads? Also, how will we deal with scaling?
	 * @param userID
	 * @param url
	 */
	public static void setProfilePicture(int userID, String url) {
		// TODO: Finish me
	}
	
	public static List<Friendship> getFriendRequests(String username) {
		int userID = User.getIDFromUsername(username);
		String sql = String.format("SELECT * FROM friendships WHERE user2 = '%d' AND status = '%s';", userID, FriendshipStatus.REQUEST_SENT);
		
		ResultSet results = DBConnection.getInstance().executeQuery(sql);		
		List<Friendship> requests = new ArrayList<Friendship>();
		try {
			while (results.next()) {
				int user1 = results.getInt("user1");
				int user2 = results.getInt("user2");
				FriendshipStatus status = FriendshipStatus.valueOf(results.getString("status"));
				Friendship friendship = new Friendship(user1, user2, status);
				requests.add(friendship);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return requests;
	}
	
	public static List<User> getAllUsers() {
		String sql = "SELECT * FROM users;";
		DBConnection.getInstance().executeQuery(sql);
		ResultSet results = DBConnection.getInstance().executeQuery(sql);	
		
		List<User> users = new ArrayList<User>();
		try {
			while (results.next()) {
				int id = results.getInt("id");
				String username = results.getString("username");
				String photoFilename = results.getString("picturefile");
				User user = new User(id, username, photoFilename);
				users.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}
	
	public static List<User> search(String query) {
		String sql = String.format("SELECT * FROM users WHERE USERNAME LIKE '%s%%';", query);
		DBConnection.getInstance().executeQuery(sql);
		ResultSet results = DBConnection.getInstance().executeQuery(sql);	
		
		List<User> searchResults = new ArrayList<User>();
		try {
			while (results.next()) {
				int id = results.getInt("id");
				String username = results.getString("username");
				String photoFilename = results.getString("picturefile");
				User user = new User(id, username, photoFilename);
				searchResults.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchResults;
		
	}
	
	//messages 
	
	//challenges
	
	//notes
	
	//history
	
	//created quizzes
	
	public static void promoteUserToAdmin(int userID) {     
		String query = "UPDATE user SET isAdmin = 1 WHERE id = " + userID;
		DBConnection.getInstance().executeQuery(query);
	}

	public static void demoteUserFromAdmin(int userID) {     
		String query = "UPDATE user SET isAdmin = 0 WHERE id = " + userID;
		DBConnection.getInstance().executeQuery(query);
	}

	public static ArrayList<User> getAdminUsers() {                
		try {                        
			ArrayList<User> results = new ArrayList<User>();                        
			String query = "SELECT * FROM user WHERE is_admin=1 LIMIT 8";                                                
			ResultSet r = DBConnection.getInstance().executeQuery(query);                        
			while(r.next()) {                                
				User u = new User(r.getInt("id"), r.getString("username"), r.getString("picturefile"));                                
				results.add(u);                        
			}                        
			return results;                
		} catch (SQLException e) {                        
			return null;                
		}        
	}

	public static void removeUser(User u) {
		int userID = u.getID();                      
		String deleteUser = "DELETE FROM users WHERE id = " + userID;
		DBConnection.getInstance().executeQuery(deleteUser);    

		String deleteAch = "DELETE FROM userAchievements WHERE username = " + getUsernameFromID(userID); 
		DBConnection.getInstance().executeQuery(deleteAch);    

		String deleteFriend = "DELETE FROM friendships WHERE user1 = " + userID + " OR `user2` = " + userID;                        
		DBConnection.getInstance().executeQuery(deleteFriend);   

		String deleteMsg = "DELETE FROM messages WHERE fromUser = " + userID + " OR toUser = " + userID;  
		DBConnection.getInstance().executeQuery(deleteMsg);

		String deleteCreator = "DELETE FROM quizzes WHERE creator = " + getUsernameFromID(userID);                      
		DBConnection.getInstance().executeQuery(deleteCreator);

		String deleteScore = "DELETE FROM scores WHERE username = " + getUsernameFromID(userID); 
		DBConnection.getInstance().executeQuery(deleteScore);
	}
	
	//Get user's recently taken quizzes
	public ArrayList<Quiz> homepageGetUserRecentQuizzes(int userID){
		ArrayList<Quiz> result = new ArrayList<Quiz>();
		//String username = getUsernameFromID(userID);
		String query = "SELECT dateCeated, COUNT(*) FROM quizzes WHERE username = " + username +  " ORDER BY COUNT(*) GROUP BY dateCreated LIMIT 10";
		ResultSet rs = DBConnection.getInstance().executeQuery(query);
		try{
			while(rs.next()){
				result.add(new Quiz(rs.getInt("quizID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	//Get user's recently taken quizzes
	public ArrayList<Quiz> homepageGetUserRecentCreated(int quizID){
		ArrayList<Quiz> result = new ArrayList<Quiz>();
		String username = getUsernameFromID(quizID);
		String query = "SELECT dateCeated, COUNT(*) FROM scores WHERE username = " + username +  " ORDER BY COUNT(*) GROUP BY dateCreated LIMIT 10";
		ResultSet rs = DBConnection.getInstance().executeQuery(query);
		try{
			while(rs.next()){
				result.add(new Quiz(rs.getInt("quizID")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	/**
	 * Given a plan-text password, generates a SHA hash.
	 * @param pass
	 * @return
	 */
	private static String generatePasswordHash(String pass) {
		String hash = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			md.update(pass.getBytes());
			byte[] byteData = md.digest();
			hash = hexToString(byteData);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return hash;
	}
	
	/*
	 Given a byte[] array, produces a hex String,
	 such as "234a6f". with 2 chars for each byte in the array.
	 (provided code)
	 Credit: assignment 3 starter code
	*/
	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	
	
}
