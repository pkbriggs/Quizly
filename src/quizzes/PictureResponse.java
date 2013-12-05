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
			ArrayList<String> answers = getParameters(request, "answer");

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
	public int score(HttpServletRequest request) {
		ArrayList<String> responses = null;		
		try{
			responses = getParameters(request, "answer"+questionID);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return 0;
		}
		
		if(responses.size() != 1)
			System.out.println("There was an issue. there should only be responses of size 1 ln 132");
		String response = responses.get(0);
		ArrayList<String> correct_responses = GetCorrectResponses(response);
		if(correct_responses.contains(response))
			return 1;
		else 
			return 0;
	}
	
	private ArrayList<String> GetCorrectResponses(String string) {
		return new ArrayList<String>(Arrays.asList(string.split("[|]")));
	}
	
	private ArrayList<String> getParameters(HttpServletRequest request, String id)
		throws Exception{
		ArrayList<String> array = new ArrayList<String>();
		try{
			String[] responses = request.getParameterValues(id);

			if(responses == null){
				throw new Exception("No answers checked. Please try again.");
			}
			
			System.out.println("Got the responses" + responses.toString());

			for(int i = 0; i< responses.length;i++){
				String response = responses[i];
				
				if(response.equals(null)){
					System.out.println("Response was null -- wrong id perhaps?");
					break;
				}

				response = response.trim();
				response = response.toLowerCase();

				if(!response.equals(""))
					array.add(response);
				
			}
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		return array;
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
	
	@Override
	public int numAnswers() {
		return 1;
	}
}
