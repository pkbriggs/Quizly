 package quizzes;

import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import dbconnection.DBConnection;

/**
 * A static class the provides helper methods for dealing with questions
 * associated with quizzes from the database 
 */
public class Question {

	public static void NewMultipleChoice(HttpServletRequest request){
		DBConnection connection  = DBConnection.GetConnection(request);
		Integer quizID = (Integer) DBConnection.GetSessionAttribute(request, "quiz_being_created");
		
		String choice1 = request.getParameter("choice1");
		String choice2 = request.getParameter("choice2");
		String choice3 = request.getParameter("choice3");
		String choice4 = request.getParameter("choice4");
		String answer = request.getParameter("radio");
		String question = request.getParameter("question");
		System.out.println("Quiz ID = " + quizID); 
		connection.executeQuery("INSERT INTO multiple_choice"
				+ "(quizID, question, answer, choice1, choice2, choice3, choice4)"
				+ " VALUES(\""+String.valueOf(quizID)+"\", \""+question+"\", \""+answer+"\", "
						+ "\""+choice1+"\", \""+choice2+"\", \""+choice3+"\", \""+choice4+"\")");

	}

	public static void NewFillInTheBlank(HttpServletRequest request) {
		DBConnection connection  = DBConnection.GetConnection(request);
		System.out.println("DBConnection = " + connection );
		Integer quizID = (Integer) DBConnection.GetSessionAttribute(request, "quiz_being_created");
		System.out.println("QuizID = " + quizID );

		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		
		String query = "INSERT INTO fill_in_the_blank"
				+ "(quizID, question, answer) VALUES("
				+ "\""+String.valueOf(quizID)+"\", \""+question+"\", \""+answer+"\")";
		
		connection.executeQuery(query);
	}

	public static void NewQuestionResponse(HttpServletRequest request) {
		DBConnection connection  = DBConnection.GetConnection(request);
		Integer quizID = (Integer) DBConnection.GetSessionAttribute(request, "quiz_being_created");

		String question = request.getParameter("question");
		String answer = request.getParameter("answer");
		String query = "INSERT INTO question_response"
				+ "(quizID, question, answer) VALUES("
				+ "\""+String.valueOf(quizID)+"\", \""+question+"\", \""+answer+"\")";
		System.out.println("Query " + query);
		connection.executeQuery(query);
		
	}

	public static void NewPictureResponse(HttpServletRequest request) {
		DBConnection connection  = DBConnection.GetConnection(request);
		Integer quizID = (Integer) DBConnection.GetSessionAttribute(request, "quiz_being_created");

		String imageURL = request.getParameter("question");
		String answer = request.getParameter("answer");
		
		connection.executeQuery("INSERT INTO picture_response"
				+ "(quizID, imageURL, answer) VALUES("
				+ "\""+String.valueOf(quizID)+"\", \""+imageURL+"\", \""+answer+"\")");
	}
	
	
}
