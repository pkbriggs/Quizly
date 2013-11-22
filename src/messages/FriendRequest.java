package messages;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendRequest extends Message{

	public FriendRequest(int id, int userIDFrom, int userIDTo, String message) {
		super(id, userIDFrom, userIDTo, message);
		// TODO Auto-generated constructor stub
		mType = 2;
		addToRecieved();
	}

}