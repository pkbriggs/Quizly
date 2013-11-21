package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class QuestionResponse implements Question {
	
	private static final int type = Question.QUESTION_RESPONSE;
	private String question;
	private String answer;
	private int quizID;
	private int questionID;
	
	QuestionResponse(HttpServletRequest request)
		throws Exception{
		try{
			this.question = SanitizeQuestion(request);
			this.answer = SanitizeAnswer(request);
		}catch(Exception e){
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
	
	QuestionResponse(ResultSet rs, int questionID){
		this.questionID = questionID;
		try {
			this.answer = rs.getString("answer");
			this.question = rs.getString("question");
			this.quizID = rs.getInt("quizID");
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
		if(answer.equals("")){
			throw new Exception("No answer provided. Please try again.");
		}
		return answer;
	}
	
	@Override
	public void saveToDatabase(DBConnection connection) {

		String query = "INSERT INTO question_response"
				+ "(quizID, question, answer) VALUES("
				+ "\""+this.quizID+"\", \""+this.question+"\", \""+this.answer+"\")";
		System.out.println("Question response query = "+ query);
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

