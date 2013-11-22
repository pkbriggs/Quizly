package messages;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Challenge extends Message{
	
	protected int quizId;
	protected int resultID;

	public Challenge(int ID, int userIDFrom, int userIDTo, String message, int quizID) {
		super(ID, userIDFrom, userIDTo, message);
		mType = 1;
		quizId = quizID;
		resultID = getBestScore();
		this.ID = ID;
		addToRecieved();
	}

	/**
	 * Query the database for the challenging user's best score on the quiz.
	 */
	public int getBestScore(){
		//User challenger = 
		//List<Integer> userQuizzes = challenger.getQuizzesTaken();
		return 0;
	}
	/**
	 * Provide a link to the quiz.
	 */
	public String getLink(){
		String link = "link";
		return link;
	}

}