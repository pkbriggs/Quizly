package users;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @class User
 * This will be a static class will be stored as a context attribute 
 * that will interact with the database to get all the information related
 * to users
 */
public class User {

	/**
	 * Given a @userID, will compare the hashed @pass to the hashed password associated with the user's account.
	 * @param userID
	 * @param pass the plain-text password
	 * @return true if the password is correct, false otherwise
	 */
	public static boolean checkPassword(int userID, String pass) {
		String sql = String.format("SELECT passwordhash FROM users WHERE id = %d;", userID);
		ResultSet results = null; // db.executeQuery(sql)
		
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
		String hashedPass = generatePasswordHash(pass);
		String sql = String.format("INSERT INTO users (username, passwordhash) VALUES ('%s', '%s');", username, hashedPass);
		// db.executeQuery(sql)
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
	
	//messages 
	
	//challenges
	
	//notes
	
	//history
	
	//created quizzes
	
	
	// Private helper methods
	
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
