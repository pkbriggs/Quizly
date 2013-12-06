package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class QuestionResponse implements Question {
	
	private static final int type = Question.QUESTION_RESPONSE;
	private static final String parse_char = "%";

	private String question;
	private ArrayList<String> answers;
	private int quizID;
	private int questionID;
	private int num_responses;
	private int ordered;
	private ArrayList<String> user_responses;
	private int score;
	
	QuestionResponse(HttpServletRequest request)
		throws Exception{
		try{
			this.user_responses = new ArrayList<String>();

			String num_responses_str = request.getParameter("num_responses");
			String multiple_responses = request.getParameter("multiple_responses");

			if(multiple_responses != null){
				if(num_responses_str.equals(null))
					throw new Exception("Please indicate how many responses the question should ask for. Go back and try again.");
				else
					this.num_responses = Integer.parseInt(num_responses_str);
				
				String ordered_checked = request.getParameter("ordered");	
				if(ordered_checked != null)
					this.ordered = DBConnection.TRUE;
				else
					this.ordered = DBConnection.FALSE;
			}else{
				this.ordered = DBConnection.FALSE;
				this.num_responses = 1;
			}
			
			this.question = SanitizeQuestion(request);
			this.answers = SanitizeAnswer(request);
			
			if(this.ordered == DBConnection.TRUE && this.num_responses != this.answers.size())
				throw new Exception("For an ordered question, the number of responses you provide should be the same as the number of responses you provide in the number of responses text box.");
			
			
		}catch(Exception e){
			throw new Exception(e.getMessage());
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
	
	private String SanitizeQuestion(HttpServletRequest request) 
			throws Exception{
		String question = "";
		try{
			question = request.getParameter("question");
		}catch(Exception e){
			System.out.println("in santizize question: "+e.getMessage());
			throw new Exception(e.getMessage());
		}
		if(question.equals("")){
			throw new Exception("No question provided. Please try again.");
		}
		return question;
	}
	
	QuestionResponse(ResultSet rs, int questionID){
		this.questionID = questionID;
		try {
			this.answers = ParseAnswers(rs);
			this.score = 0;
			this.user_responses = new ArrayList<String>();
			this.question = rs.getString("question");
			this.quizID = rs.getInt("quizID");
			this.ordered = rs.getInt("ordered");
			this.num_responses = rs.getInt("num_responses");
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
	private ArrayList<String> SanitizeAnswer(HttpServletRequest request)
			throws Exception{
		ArrayList<String> answers = new ArrayList<String>();

		try{
			answers = getParameters(request, "answer");
			if(answers.size() == 0)
				throw new Exception("No answer provided. Please try again.");
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		boolean empty = false;
		try{
			empty = answers.size() == 0;
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		if(empty){
			throw new Exception("No answer provided. Please try again.");
		}
		return answers;
	}
	
	@Override
	public void saveToDatabase() {
		DBConnection connection = DBConnection.getInstance();

		String answer_str = GetAnswerString();
		String query = "INSERT INTO question_response"
				+ "(quizID, question, answer, num_responses, ordered) VALUES("
				+ "\""+this.quizID+"\", \""+this.question+"\", \""+answer_str+"\","
						+ " \""+this.num_responses+"\", \""+this.ordered+"\")";
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
		int points = 0;

		try{
			responses = getParameters(request, "answer"+questionID);
		}catch(Exception e){
			System.out.println(e.getMessage());
			this.score = 0;
			return this.score;
		}
		
		if(responses.size() > this.num_responses)
			System.out.println("ERORR: number of responses provided larger than the number of responses for this question");
				
		ArrayList<String> already_seen = new ArrayList<String>();
		for(int i = 0; i < responses.size(); i++){
			String response = responses.get(i);
			int start = (this.ordered == DBConnection.TRUE)? i : 0;
			int end = (this.ordered == DBConnection.TRUE)? i+1 : this.answers.size();
			
			for(int j = start; j < end; j++){
				ArrayList<String> correct_responses = GetCorrectResponses(this.answers.get(j));
				if(correct_responses.contains(response) && !already_seen.contains(response)){
					points++;
					for(String variation: correct_responses){
						already_seen.add(variation);
					}
				}
			}
		}
		
		this.score = points;
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
			
			PrintParameterNames(request);
			if(responses == null){
				throw new Exception("No answers checked. Please try again.");
			}
			
			for(int i = 0; i< responses.length;i++){
				String response = responses[i];

				if(response.equals(null))
					throw new Exception("Response was null. strange. Error occured.");

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
	
	private void PrintParameterNames(HttpServletRequest request){
		Enumeration<String> names = request.getParameterNames();
		while(names.hasMoreElements()){
			String name = names.nextElement();
			System.out.println("name: " + name);
		}
	}

	@Override
	public int numAnswers() {
		return this.num_responses;
	}
	
	@Override
	public String getCorrectResponses() {
		String str = "";
		
		for(int i = 0; i < this.answers.size(); i++ ){
			String answer = this.answers.get(i).replace("|", " OR ");
			
			if(this.ordered == DBConnection.TRUE){
				str += i+ ") ";
			}
			
			str += "[" + answer + "]";
			
			if(i < this.answers.size()-1)
				str += "<br>";
		}
		
		return str;
	}
	
	@Override
	public String getUserResponses() {
		String str = "";
		for(int i = 0; i < this.user_responses.size(); i++ ){
			if(this.ordered == DBConnection.TRUE){
				str += i+ ") ";
			}
			
			str += "[" + this.user_responses.get(i) + "]";
			
			if(i < this.user_responses.size()-1)
				str += "<br>";
		}
		
		return str;
	}
	
	@Override
	public int getScore(){
		return this.score;
	}
}

