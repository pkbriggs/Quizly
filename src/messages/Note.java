package messages;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Note extends Message{

	private int hasBeenRead;
	
	public Note(int ID, int userIDFrom, int userIDTo, String message) {
		super(ID, userIDFrom, userIDTo, message);
		mType = 0;
		hasBeenRead = 0;
		addToRecieved();
	}
	
	public int getRead(){
		return hasBeenRead;
	}
	
	public void setRead(){
		hasBeenRead = 1;
	}
}