 package quizzes;

import java.util.*;

/*
 * This is the interface that all implementations of the type quiz will implement
 */
public interface Question {

	
	/**
	 * Returns the number of points this answer should receive for
	 * the question. Allows for questions which may have multiple possible 
	 * answers.
	 * @param answers
	 * @return
	 */
	int numPoints(ArrayList<String> answers);

	/**
	 * Sets the correct answer(s) to this question. 
	 * @param answer
	 */
	void setCorrectAnswer(List<String> correct_answers);
	
	/**
	 * Sets the question. In the case of a picture-response question
	 * this may be a URL.
	 * @param question
	 */
	void setQuestion(String question);
	
	/**
	 * Gets the number of points this question is worth
	 */
	int worth();
	
	/**
	 * Sets the number of points this question is worth
	 */
	void setWorth(int worth);
}
