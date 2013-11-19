package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class FillInTheBlank implements Question {

	private static final int type = Question.FILL_IN_THE_BLANK;
	private String question;
	private String answer;
	private int quizID;	
	private int questionID;
	
	FillInTheBlank(HttpServletRequest request){
		this.question = request.getParameter("question");
		this.answer = request.getParameter("answer");
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
