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
	
	QuestionResponse(HttpServletRequest request){
		this.question = request.getParameter("question");
		this.answer = request.getParameter("answer");
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
	public String getAnswer() {
		return this.answer;
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

