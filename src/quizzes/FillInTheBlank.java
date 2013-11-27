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
	
	FillInTheBlank(HttpServletRequest request)
			throws Exception{
		try{
			this.question = SanitizeQuestion(request);
			this.answers = SanitizeAnswer(request);
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
			ArrayList<String> answers = new ArrayList<String>();
			String num_answer_str = request.getParameter("fib_num_answers");
			System.out.println("numanswers for fib = "+ num_answer_str);
			int num_answers = Integer.parseInt(num_answer_str);
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
