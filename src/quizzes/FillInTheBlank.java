package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class FillInTheBlank implements Question {

	private static final int type = Question.FILL_IN_THE_BLANK;
	private static final String parse_char = "%";

	private String question;
	private ArrayList<String> answers;
	private int quizID;	
	private int questionID;
	private int score ;
	private ArrayList<String> user_responses;
	
	FillInTheBlank(HttpServletRequest request)
			throws Exception{
		try{
			this.question = SanitizeQuestion(request);
			this.answers = SanitizeAnswer(request);
			this.user_responses = new ArrayList<String>();
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}

	}
	
	FillInTheBlank(ResultSet rs, int questionID){
		try {
			this.answers = ParseAnswers(rs);
			this.question = rs.getString("question");
			this.quizID = rs.getInt("quizID");
			this.questionID = questionID;
			this.score =0;
			this.user_responses = new ArrayList<String>();
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
	/**
	 * Takes in a @request and returns a sanitized answer
	 * @param request
	 * @return
	 */
	private ArrayList<String> SanitizeAnswer(HttpServletRequest request)
			throws Exception{
			String num_answer_str = request.getParameter("fib_num_answers");
			System.out.println("numanswers for fib = "+ num_answer_str);
			ArrayList<String> answers = getParameters(request, "answer");

			return answers;
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
		Pattern regex = Pattern.compile("[_]+");
		Matcher matcher = regex.matcher(question);
		
		//boolean blankFound = Pattern.matches(" _+", "aaaaab");
		if(!matcher.find())
			throw new Exception("The question must contain a blank \"___\". Please try again");
		
		return question;
	}
	
	@Override
	public void saveToDatabase() {
		DBConnection connection = DBConnection.getInstance();

		String answer_string = GetAnswerString();
		String query = "INSERT INTO fill_in_the_blank"
				+ "(quizID, question, answer) VALUES("
				+ "\""+this.quizID+"\", \""+this.question+"\", \""+answer_string+"\")";
		
		System.out.println("fill in the blank query: "+ query);
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
		return this.question;
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
		for(int i = 0; i < this.answers.size();i++){
			str += " Answer"+i+ " : "+ this.answers.get(i) + " \n";
		}
		return str;
	}

	@Override
	public int score(HttpServletRequest request) {
		ArrayList<String> responses = null;		
		try{
			responses = getParameters(request, "answer"+questionID);
		}catch(Exception e){
			System.out.println(e.getMessage());
			this.score = 0;
			return this.score;
		}
		
		if(responses.size() != 1){
			System.out.println("There was an issue. there should only be responses of size 1 ln 132");
			this.score = 0;
			return this.score;
		}
		
		String response = responses.get(0);
		ArrayList<String> correct_responses = GetCorrectResponses(response);
		if(correct_responses.contains(response)){
			this.score = 1;
		}
		else {
			this.score = 0;
		}
		
		return this.score;

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
				throw new Exception("FIB: No answers provided for: id = " + id + ". Please try again.");
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

				if(!response.equals("")){
					this.user_responses.add(response);
					array.add(response);
				}
				
			}
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		return array;
	}
	
	@Override
	public int numAnswers() {
		return this.answers.size();
	}
	
	@Override
	public String getCorrectResponses() {
		String str = "";
		
		for(int i = 0; i < this.answers.size(); i++ ){
			String answer = this.answers.get(i).replace("|", " OR ");
			str += "[" + answer + "]";
			
			if(i < this.answers.size()-1)
				str += " , ";
		}
		
		return str;
	}
	
	@Override
	public String getUserResponses() {
		String str = "";
		for(int i = 0; i < this.user_responses.size(); i++ ){
			str += "[" + this.user_responses.get(i) + "]";
			
			if(i < this.user_responses.size()-1)
				str += " , ";
		}
		
		return str;
	}
	
	@Override
	public int getScore(){
		return this.score;
	}
}
