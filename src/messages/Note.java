package messages;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Note extends Message{

	private boolean hasBeenRead;
	
	public Note(int userIDFrom, int userIDTo, String message) {
		super(userIDFrom, userIDTo, message);
		mType = 0;
		hasBeenRead = false;
	}
	
	public boolean getRead(){
		return hasBeenRead;
	}
	
	public void setRead(){
		hasBeenRead = true;
	}
}