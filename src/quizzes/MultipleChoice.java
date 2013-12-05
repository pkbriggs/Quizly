package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class MultipleChoice implements Question {

	private static final int type = Question.MULTIPLE_CHOICE;
	private static final String parse_char = "%";

	private String question;
	private ArrayList<String> answers;
	private ArrayList<String> choices;
	private int quizID;
	private int questionID;
	
	MultipleChoice(HttpServletRequest request)
		throws Exception{
		try{
			System.out.println("Making MC question");
			this.choices = SanitizeChoices(request);
			System.out.println("Choices sanitized");
			this.answers = SanitizeAnswers(request);
			System.out.println("Answers sanitized");

			this.question = SanitizeQuestion(request);

		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
	}

	private String SanitizeQuestion(HttpServletRequest request) 
		throws Exception{
		String question = request.getParameter("question");
		if(question.equals("")){
			throw new Exception("No question provided. Please try again.");
		}
		return question;
	}

	private ArrayList<String> SanitizeChoices(HttpServletRequest request) 
		throws Exception{
		ArrayList<String >choices = new ArrayList<String>();
		String choice1 = request.getParameter("choice0");
		String choice2 = request.getParameter("choice1");
		String choice3 = request.getParameter("choice2");
		String choice4 = request.getParameter("choice3");

		if(choice1.equals("") ||choice2.equals("")||choice3.equals("")||choice4.equals("")){
			throw new Exception("One of the choices was left blank. Please try again.");
		}
		
		choices.add(choice1);
		choices.add(choice2);
		choices.add(choice3);
		choices.add(choice4);
		return choices;
	}

	/**
	 * Takes in a @request and returns a sanitized answer
	 * @param request
	 * @return
	 */
	private ArrayList<String> SanitizeAnswers(HttpServletRequest request)
		throws Exception{
		ArrayList<String> answers = null;
		try{
			answers = getParameters(request, "answer");		
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		return answers;
	}
	
	MultipleChoice(ResultSet rs, int questionID){
		this.choices = new ArrayList<String>();
		this.questionID = questionID;
		try {
			this.answers = ParseAnswers(rs);
			this.question = rs.getString("question");
			this.quizID = rs.getInt("quizID");
			this.choices.add(rs.getString("choice1"));
			this.choices.add(rs.getString("choice2"));
			this.choices.add(rs.getString("choice3"));
			this.choices.add(rs.getString("choice4"));

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
	public void saveToDatabase() {
		DBConnection connection = DBConnection.getInstance();
		
		String query = "INSERT INTO multiple_choice"
				+ "(quizID, question, answer, choice1, choice2, choice3, choice4)"
				+ " VALUES(\""+String.valueOf(quizID)+"\", \""+question+"\", \""+GetAnswerString()+"\", "
						+ "\""+choices.get(0)+"\", \""+choices.get(1)+"\", \""+choices.get(2)+"\", \""+choices.get(3)+"\")";
		System.out.println("multiple choice query: "+ query);
	
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
	public int score(HttpServletRequest request) {
		ArrayList<String> responses = null;		
		int points = 0;

		try{
			responses = getParameters(request, "answer"+questionID);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return 0;
		}
		
		for(int i = 0; i < this.answers.size(); i++){
			if(responses.contains(this.answers.get(i))){
				points++;
				boolean removed = responses.remove(this.answers.get(i));
				if(!removed)
					System.out.println("Problem removing response! This is a bug. ln163");
			}
		}
		
		System.out.println("returning score!" + points);
		return points;
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
				if(request.getParameter("create_quiz") != null)
					response = request.getParameter(responses[i]);
				System.out.println("This is the value of the radio button " + i + " : " + response);
				System.out.println("This response is being recorded: " + response);
				if(response.equals(null)){
					System.out.println("Response was null -- wrong id perhaps?");
					break;
				}

				response = response.trim();
				System.out.println("Trimmed the response");

				response = response.toLowerCase();
				System.out.println("lower cased the response");

				if(!response.equals(""))
					array.add(response);
				
				System.out.println("added the response");

			}
		}catch(Exception e){
			throw new Exception(e.getMessage());
		}
		
		return array;
	}
	
	
	public ArrayList<String> getChoices(){
		return this.choices;
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
		str += " Answers: " + this.answers.toString() + " \n";
		str += " Choice1: " + this.choices.get(0);
		str += " Choice2: " + this.choices.get(1);
		str += " Choice3: " + this.choices.get(2);
		str += " Choice4: " + this.choices.get(3);
		return str;
	}
	
	@Override
	public int numAnswers() {
		return this.answers.size();
	}
}

