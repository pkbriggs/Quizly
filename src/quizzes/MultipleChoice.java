package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import dbconnection.DBConnection;

public class MultipleChoice implements Question {

	private static final int type = Question.MULTIPLE_CHOICE;
	
	private String question;
	private String answer;
	private ArrayList<String> choices;
	private int quizID;
	private int questionID;
	
	MultipleChoice(HttpServletRequest request){
		this.choices = new ArrayList<String>();
		this.choices.add(request.getParameter("choice1"));
		this.choices.add(request.getParameter("choice2"));
		this.choices.add(request.getParameter("choice3"));
		this.choices.add(request.getParameter("choice4"));
		this.answer = request.getParameter("radio");
		this.question = request.getParameter("question");
	}
	
	MultipleChoice(ResultSet rs, int questionID){
		this.choices = new ArrayList<String>();
		this.questionID = questionID;
		try {
			this.answer = rs.getString("answer");
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
	@Override
	public void saveToDatabase(DBConnection connection) {
				
		String query = "INSERT INTO multiple_choice"
				+ "(quizID, question, answer, choice1, choice2, choice3, choice4)"
				+ " VALUES(\""+String.valueOf(quizID)+"\", \""+question+"\", \""+answer+"\", "
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
	public String getAnswer() {
		return this.answer;
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
}

