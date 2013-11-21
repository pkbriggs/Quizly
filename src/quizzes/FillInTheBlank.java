package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class FillInTheBlank implements Question {

	private static final int type = Question.FILL_IN_THE_BLANK;
	private String question;
	private String answer;
	private int quizID;	
	private int questionID;
	
	FillInTheBlank(HttpServletRequest request)
			throws Exception{
		try{
			this.question = SanitizeQuestion(request);
			this.answer = SanitizeAnswer(request);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}

	}
	
	FillInTheBlank(ResultSet rs, int questionID){
		try {
			this.answer = rs.getString("answer");
			this.question = rs.getString("question");
			this.quizID = rs.getInt("quizID");
			this.questionID = questionID;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Takes in a @request and returns a sanitized answer
	 * @param request
	 * @return
	 */
	private String SanitizeAnswer(HttpServletRequest request)
			throws Exception{
		String answer = request.getParameter("answer");
		answer = answer.trim();
		answer = answer.toLowerCase();
		if(answer.equals(""))
			throw new Exception("No answer provided.");
		return answer;
	}
	
	
	/**
	 * Takes in a @request and returns a sanitized answer
	 * @param request
	 * @return
	 */
	private String SanitizeQuestion(HttpServletRequest request)
			throws Exception{
		String question = request.getParameter("question");
		question = question.trim();
		question = question.toLowerCase();
		boolean blankFound = Pattern.matches(" _+[^a-zA-Z0-9]", "aaaaab");
		if(!blankFound)
			throw new Exception("The question must contain a blank \"___\". Please try again");
		
		return question;
	}
	
	@Override
	public void saveToDatabase(DBConnection connection) {
		String query = "INSERT INTO fill_in_the_blank"
				+ "(quizID, question, answer) VALUES("
				+ "\""+this.quizID+"\", \""+this.question+"\", \""+this.answer+"\")";
		
		System.out.println("fill in the blank query: "+ query);
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

	@Override
	public int getQuestionID() {
		return this.questionID;
	}

	@Override
	public int getType() {
		return type;
	}
}
