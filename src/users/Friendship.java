package users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import dbconnection.DBConnection;

/**
 * @class Friendship
 * This is a static class that should be used to manage the "friendship" status
 * between users.
 */
public class Friendship {
	private int userOne, userTwo;
	private FriendshipStatus status;
	
	/**
	 * An enum that allows for easy categorization of a friendship status
	 * between two users. 
	 */
	public enum FriendshipStatus { FRIENDS, REQUEST_SENT, NOT_FRIENDS };
	
	
	public Friendship(int userOne, int userTwo, FriendshipStatus status) {
		this.userOne = userOne;
		this.userTwo = userTwo;
		this.status = status;
	}
	
	public int getInitiatingUser() {
		return userOne;
	}
	
	// static methods

	/**
	 * Will "approve" a friend request sent from @otherID to @userID by
	 * changing the @status stored in the database from REQUEST_SENT
	 * to FRIENDS.
	 * @param userID the user accepting the friend request
	 * @param otherID the user that initiated the friend request
	 */
	public static void acceptFriendRequest(int userID, int otherID) {
		String sql = String.format("UPDATE friendships SET status = '%s' WHERE user1 = '%d' AND user2 = '%d';", otherID, userID, FriendshipStatus.FRIENDS.name());
		// TODO: Finish me
	}
	
	/**
	 * Will reject a friend request sent from @otherID to @userID by
	 * deleting the request. This means that it is possible for
	 * @otherID to send @userID to send another request (there
	 * is no history of the rejection or blocking).
	 * @param userID the user rejecting the friend request
	 * @param otherID the user that initiated the friend request
	 */
	public static void rejectFriendRequest(int userID, int otherID) {
		// TODO: Finish me
	}
	
	/**
	 * This is called if one friend attempts to "friend" another person. This will
	 * create a new entry in the @friends database table as so:
	 *           user1       user2         status
	 *          @userID     @otherID    "NOT_FRIENDS"
	 * It must be noted that the @status is stored as a String. This is done because 
	 * it 1) makes debugging easier, 2) allows more types to be added seamlessly, and
	 * 3) allows for easier conversion when fetching the value from the database.
	 * 
	 * Does not currently check for duplicates.
	 * 
	 * @param userID the id of the user initiating the friend request
	 * @param otherID the id of the user receiving the request
	 */
	public static void sendFriendRequest(int userID, int otherID) {
		String sql = String.format("INSERT INTO friendships (user1, user2, status) VALUES ('%d', '%d', '%s');", userID, otherID, FriendshipStatus.REQUEST_SENT.name());
		DBConnection.getInstance().executeQuery(sql);
	}
	
	/**
	 * Returns the friendship status between the two given users (given by id).
	 * The status is returned in the form of a @FriendshipStatus.
	 * @param userID
	 * @param otherID
	 * @return
	 */
	public static FriendshipStatus getFriendship(int userID, int otherID) {
		String sql = String.format("SELECT status FROM friendships WHERE (user1 = '%d' AND user2 = '%d') OR (user1 = '%d' AND user2 = %d);", userID, otherID, otherID, userID);
		ResultSet results = null; // db.executeQuery(sql)
		// TODO: Finish me
		
		try {
			if (results.next()) {
				String status = results.getString("status");
				return FriendshipStatus.valueOf(status);
			} else {
				// this happens if there is no record of their friendship, thus the users are not friends
				return FriendshipStatus.NOT_FRIENDS;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return FriendshipStatus.NOT_FRIENDS;
	}	
}
