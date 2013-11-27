package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class QuestionResponse implements Question {
	
	private static final int type = Question.QUESTION_RESPONSE;
	private static final String parse_char = "%";

	private String question;
	private ArrayList<String> answers;
	private int quizID;
	private int questionID;
	
	QuestionResponse(HttpServletRequest request)
		throws Exception{
		try{
			this.question = SanitizeQuestion(request);
			this.answers = SanitizeAnswer(request);
			System.out.println("successfullly sanitized both");

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
	private ArrayList<String> SanitizeAnswer(HttpServletRequest request)
			throws Exception{
		ArrayList<String> answers = new ArrayList<String>();

		try{
			int num_answers = Integer.parseInt(request.getParameter("qr_num_answers"));
			System.out.println("numanswers for qr = "+ num_answers);
			for(int i = 0; i< num_answers;i++){
				String answer = request.getParameter("answer"+i);
				answer = answer.trim();
				answer = answer.toLowerCase();

				if(!answer.equals(""))
					answers.add(answer);
			}
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
				+ "(quizID, question, answer) VALUES("
				+ "\""+this.quizID+"\", \""+this.question+"\", \""+answer_str+"\")";
		System.out.println("Question response query = "+ query);
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
		String str = "Question: " + this.question + " id: "+ this.questionID + " QuizID: "+ this.quizID;
		for(int i = 0; i < this.answers.size();i++){
			str += " Answer"+i+ " : "+ this.answers.get(i) + " \n";
		}
		return str;
	}
}

