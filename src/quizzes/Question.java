package quizzes;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public interface Question {

	public static final int MULTIPLE_CHOICE = 1;
	public static final int QUESTION_RESPONSE = 2;
	public static final int PICTURE_RESPONSE = 3;
	public static final int FILL_IN_THE_BLANK = 4;
	
	/**
	 * Saves the question's information to the database
	 * @param connection
	 */
	public void saveToDatabase();
	
	/**
	 * Sets the quizID of the quiz this is associated with
	 * @param quizID
	 */
	public void setQuizID(int quizID);
	
	/**
	 * Returns the question associated with this question
	 * @return
	 */
	public String getQuestion();
	
	/**
	 * Returns the questionID associated with this question
	 * @return
	 */
	public int getQuestionID();
	
	/**
	 * Returns the type associated with this question
	 * @return
	 */
	public int getType();
	
	/**
	 * Returns the user's score for that particular question given their
	 * response encoded in the @request
	 * @param request
	 * @return
	 */
	public int score(HttpServletRequest request);
	
	/**
	 * Returns the total number of answers to this question
	 * @return
	 */
	public int numAnswers();
	
	/**
	 * Returns the correct responses in the form of a list-like string
	 * @return
	 */
	public String getCorrectResponses();
	
	/**
	 * Returns all the responses the user provided to this question in 
	 * a list-like string.
	 * @return
	 */
	public String getUserResponses();
	
	/**
	 * Returns the score the user got on this question
	 * @return
	 */
	public int getScore();
}
