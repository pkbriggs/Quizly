package quizzes;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import dbconnection.DBConnection;

/*
 * This class will store a series of questions of various types that 
 * can then 
 */
public class Quiz {

	private String description;
	private ArrayList<Question> questions;
	private int id;
	
	/**
	 * Pushes the given quiz into the qizzes table of the database
	 * and returns the id of that quiz. Takes in a quiz and the servlet context
	 * @param quiz, context
	 */
	public static int pushQuizToDatabase(Quiz quiz, ServletContext context){
		DBConnection connection = (DBConnection) context.getAttribute("dbconnection");
		connection.executeQuery("INSERT INTO quizzes (values(\")");
		return 0;
	}
	
	/**
	 * Retrieves a quiz with id=id from the database by unserializing
	 * the object from the database. 
	 * @param id
	 * @return
	 */
	public static Quiz retrieveQuizFromDatabase(int id){
		return null;
	}
	
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
