package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class PictureResponse implements Question {

	private static final int type = Question.PICTURE_RESPONSE;
	
	private String imgURL;
	private String answer;
	private int quizID;
	private int questionID;
	
	PictureResponse(HttpServletRequest request){
		this.imgURL = request.getParameter("question");
		this.answer = request.getParameter("answer");
	}
	
	PictureResponse(ResultSet rs, int questionID){
		this.questionID = questionID;
		try {
			this.answer = rs.getString("answer");
			this.imgURL = rs.getString("imageURL");
			this.quizID = rs.getInt("quizID");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveToDatabase(DBConnection connection) {
		String query = "INSERT INTO picture_response"
				+ "(quizID, imageURL, answer) VALUES("
				+ "\""+this.quizID+"\", \""+this.imgURL+"\", \""+this.answer+"\")";
		connection.executeQuery(query);

	}

	@Override
	public void setQuizID(int quizID) {
		this.quizID = quizID;
	}
	
	@Override
	public String getQuestion() {
		return this.imgURL;
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
