package messages;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Challenge extends Message{

	public Challenge(int userIDFrom, int userIDTo, String message, int quizID) {
		super(userIDFrom, userIDTo, message);
		mType = 1;
	}

	/**
	 * Query the database for the challenging user's best score on the quiz.
	 */
	public int getBestScore(){
		int score = 0;
		return score;
	}
	/**
	 * Provide a link to the quiz.
	 */
	public String getLink(){
		String link = "link";
		return link;
	}

}