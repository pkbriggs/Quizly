package quizzes;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class PictureResponse implements Question {

	private static final int type = Question.PICTURE_RESPONSE;
	private static final String parse_char = "%";

	private String imgURL;
	private ArrayList<String> answers;
	private int quizID;
	private int questionID;
	
	PictureResponse(HttpServletRequest request)
		throws Exception{
		try{
			this.imgURL = SanitizeURL(request);
			this.answers = SanitizeAnswer(request);
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
	}
	
	PictureResponse(ResultSet rs, int questionID){
		this.questionID = questionID;
		try {
			this.answers = ParseAnswers(rs);
			this.imgURL = rs.getString("imageURL");
			this.quizID = rs.getInt("quizID");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ArrayList<String> ParseAnswers(ResultSet rs){
		String[] arr = null;
		try {
			String str = rs.getString("answer");
			arr = str.split(parse_char);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ArrayList<String>(Arrays.asList(arr));
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
	private ArrayList<String> SanitizeAnswer(HttpServletRequest request)
			throws Exception{
			ArrayList<String> answers = new ArrayList<String>();
			int num_answers = Integer.parseInt(request.getParameter("pr_num_answers"));
			System.out.println("numanswers for pr = "+ num_answers);
			for(int i = 0; i< num_answers;i++){
				String answer = request.getParameter("answer"+i);
				answer = answer.trim();
				answer = answer.toLowerCase();
				if(!answer.equals(""))
					answers.add(answer);
			}
			if(answers.size() == 0){
				throw new Exception("No answer provided. Please try again.");
			}
			return answers;
		}
	
	@Override
	public void saveToDatabase() {
		DBConnection connection = DBConnection.getInstance();

		String answer_str = GetAnswerString();
		String query = "INSERT INTO picture_response"
				+ "(quizID, imageURL, answer) VALUES("
				+ "\""+this.quizID+"\", \""+this.imgURL+"\", \""+answer_str+"\")";
		connection.executeQuery(query);

	}
	
	private String GetAnswerString(){
		String str = "";
		for(int i = 0; i < this.answers.size(); i++){
			String answer = this.answers.get(i);
			str+= answer;
			if(i < this.answers.size()-1)
				str+= parse_char;
		}
		return str;
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
		return this.answers.contains(response);
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
		String str = "Question: " + this.imgURL + " id: "+ this.questionID + " QuizID: "+ this.quizID;
		for(int i = 0; i < this.answers.size();i++){
			str += " Answer"+i+ " : "+ this.answers.get(i) + " \n";
		}
		return str;
	}
}
