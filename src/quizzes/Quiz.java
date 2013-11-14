package quizzes;

import java.util.ArrayList;

/*
 * This class will store a series of questions of various types that 
 * can then 
 */
public class Quiz {

	private String description;
	private ArrayList<Question> questions;
	
	public Quiz(){
		questions = new ArrayList<Question>();
	}
	
	/**
	 * Sets the description of this quiz
	 * @param description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Returns the description associated with this quiz
	 * @return String description
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Adds a question to the list of questions 
	 * @param Question question
	 */
	public void addQuestion(Question question){
		questions.add(question);
	}
}
