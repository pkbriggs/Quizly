package quizzes;

import java.net.URL;
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
	
	PictureResponse(HttpServletRequest request)
		throws Exception{
		try{
			this.imgURL = SanitizeURL(request);
			this.answer = SanitizeAnswer(request);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
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
	private String SanitizeURL(HttpServletRequest request)
		throws Exception{
		String imgURL = request.getParameter("question");
		try{
			URL u = new URL(imgURL); 
			u.toURI();
		}catch(Exception e){
			throw new Exception("Please enter a valid URL for the image location.");
		}
		return imgURL;
	}
	
	/**
	 * Takes in a @request and returns a sanitized answer
	 * @param request
	 * @return
	 */
	private String SanitizeAnswer(HttpServletRequest request){
		String answer = request.getParameter("answer");
		answer = answer.trim();
		answer = answer.toLowerCase();
		return answer;
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
