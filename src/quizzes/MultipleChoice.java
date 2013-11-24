package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class MultipleChoice implements Question {

	private static final int type = Question.MULTIPLE_CHOICE;
	
	private String question;
	private String answer;
	private ArrayList<String> choices;
	private int quizID;
	private int questionID;
	
	MultipleChoice(HttpServletRequest request)
		throws Exception{
		try{
		this.choices = SanitizeChoices(request);
		this.answer = SanitizeAnswer(request);		
		this.question = SanitizeQuestion(request);
		}catch(Exception e){
			System.out.println("This is e: "+ e.getMessage());
			throw new Exception(e.getMessage());
		}
	}

	private String SanitizeQuestion(HttpServletRequest request) 
		throws Exception{
		String question = request.getParameter("question");
		if(question.equals("")){
			throw new Exception("No question provided. Please try again.");
		}
		return question;
	}

	private ArrayList<String> SanitizeChoices(HttpServletRequest request) 
		throws Exception{
		ArrayList<String >choices = new ArrayList<String>();
		String choice1 = request.getParameter("choice1");
		String choice2 = request.getParameter("choice2");
		String choice3 = request.getParameter("choice3");
		String choice4 = request.getParameter("choice4");

		if(choice1.equals("") ||choice2.equals("")||choice3.equals("")||choice4.equals("")){
			throw new Exception("One of the choices was left blank. Please try again.");
		}
		
		choices.add(choice1);
		choices.add(choice2);
		choices.add(choice3);
		choices.add(choice4);
		return choices;
	}

	/**
	 * Takes in a @request and returns a sanitized answer
	 * @param request
	 * @return
	 */
	private String SanitizeAnswer(HttpServletRequest request)
		throws Exception{
		int checked = Integer.parseInt(request.getParameter("radio"));
		String answer = request.getParameter("choice"+ checked);
		if(answer.equals(null))
			throw new Exception("Correct answer for multiple choice question not selected. Please go back and try again.");
		answer = answer.trim();
		answer = answer.toLowerCase();
		return answer;
	}
	
	MultipleChoice(ResultSet rs, int questionID){
		this.choices = new ArrayList<String>();
		this.questionID = questionID;
		try {
			this.answer = rs.getString("answer");
			this.question = rs.getString("question");
			this.quizID = rs.getInt("quizID");
			this.choices.add(rs.getString("choice1"));
			this.choices.add(rs.getString("choice2"));
			this.choices.add(rs.getString("choice3"));
			this.choices.add(rs.getString("choice4"));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void saveToDatabase(DBConnection connection) {
			
		
		String query = "INSERT INTO multiple_choice"
				+ "(quizID, question, answer, choice1, choice2, choice3, choice4)"
				+ " VALUES(\""+String.valueOf(quizID)+"\", \""+question+"\", \""+answer+"\", "
						+ "\""+choices.get(0)+"\", \""+choices.get(1)+"\", \""+choices.get(2)+"\", \""+choices.get(3)+"\")";
		System.out.println("multiple choice query: "+ query);
	
		connection.executeQuery(query);

	}

	@Override
	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}

	@Override
	public String getQuestion() {
		return this.question;
	}

	@Override
	public boolean isCorrect(String response) {
		response = response.trim();
		response = response.toLowerCase();
		return this.answer.equals(response);
	}

	public ArrayList<String> getChoices(){
		return this.choices;
	}
	
	@Override
	public int getQuestionID() {
		return this.questionID;
	}
	
	@Override
	public int getType() {
		return type;
	}
	
	@Override
	public String toString(){
		String str = "Question: " + this.question + " id: "+ this.questionID + " QuizID: "+ this.quizID;
		str += " Answers: " + this.answer + " \n";
		str += " Choice1: " + this.choices.get(0);
		str += " Choice2: " + this.choices.get(1);
		str += " Choice3: " + this.choices.get(2);
		str += " Choice4: " + this.choices.get(3);
		return str;
	}
}

