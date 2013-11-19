package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import quizzes.Quiz;
import dbconnection.DBConnection;

public class Question {	

	public static final int MULTIPLE_CHOICE = 1;
	public static final int QUESTION_RESPONSE = 2;
	public static final int PICTURE_RESPONSE = 3;
	public static final int FILL_IN_THE_BLANK = 4;
	
	private int type ;
	private String html;
	private String answer;
	private String question;
	private HttpServletRequest request;
	
	Question(HttpServletRequest request, int type){
		this.request = request;
		this.type= type;
	}
	
	Question(ResultSet rs, int type, int questionIndex){
		this.type = type;
		try {
			this.answer = rs.getString("answer");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		switch(type){
		case FILL_IN_THE_BLANK:
			this.html = FillInTheBlankToHtml(rs, questionIndex);
			break;
		case MULTIPLE_CHOICE:
			this.html = MultipleChoiceToHtml(rs, questionIndex);
			break;
		case PICTURE_RESPONSE:
			this.html = PictureResponseToHtml(rs, questionIndex);
			break;
		case QUESTION_RESPONSE:
			this.html = QuestionResponseToHtml(rs, questionIndex);
			break;
		default:
			break;
		}
	}
	
	/**
	 * Returns the HTML associated with this question
	 * @return
	 */
	public String getHTML(){
		return this.html;
	}
	
	/**
	 * Returns the html information that begins each question's form
	 * @param questionIndex
	 * @param type
	 * @return
	 */
	private String formHeader(int questionIndex, int type){
		String html = "";
		html+="<form action='QuizRunner' method='post'>";
		html+="<input type='hidden' name='question' value='"+questionIndex+"'/>";
		html+="<input type='hidden' name='type' value='"+type+"'/>";
		html+="<input type='hidden' name='type' value='"+type+"'/>";
		html+="<p>" + questionIndex + ") ";
		return html;
	}
	
	private String formFooter(){
		String html= "";
		html +="</form><p>";
		return html;
	}
	
	/**
	 * Takes the information about a fill in the blank question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String FillInTheBlankToHtml(ResultSet rs, int questionIndex){
		StringBuilder html = new StringBuilder();
		html.append(formHeader(questionIndex, QUESTION_RESPONSE));
		String question ="";
		try {
			question = rs.getString("question");
			question = question.replaceAll(" _+ ", "<input type='text' name='answer'/>");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		html.append(question);
		return html.toString();
	}
	

	/**
	 * Takes the information about a multiple choice question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String MultipleChoiceToHtml(ResultSet rs, int questionIndex){
		StringBuilder html = new StringBuilder();
		html.append(formHeader(questionIndex, MULTIPLE_CHOICE));
		String question="";
		String choice1 = "";
		String choice2 = "";
		String choice3 = "";
		String choice4 = "";
		try {
			question = rs.getString("question");
			choice1 = rs.getString("choice1");
			choice2 = rs.getString("choice2");
			choice3 = rs.getString("choice3");
			choice4 = rs.getString("choice4");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		html.append(question + "<p>");
		html.append("<input type='radio' value='"+choice1+"'/><p>" );
		html.append("<input type='radio' value='"+choice2+"'/><p>" );
		html.append("<input type='radio' value='"+choice3+"'/><p>" );
		html.append("<input type='radio' value='"+choice4+"'/><p>" );
		
		html.append(formFooter());
		return html.toString();
	}
	
	/**
	 * Takes the information about a picture-response question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String PictureResponseToHtml(ResultSet rs, int questionIndex){
		StringBuilder html = new StringBuilder();
		html.append(formHeader(questionIndex, PICTURE_RESPONSE));
		String imageURL = "";
		try {
			imageURL = rs.getString("imageURL");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		html.append("<img src='"+imageURL+"'/>");
		html.append("<input type='text' name='answer'/>");
		html.append(formFooter());

		return html.toString();	
	}
	
	/**
	 * Takes the information about a question-response question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String QuestionResponseToHtml(ResultSet rs, int questionIndex){
		StringBuilder html = new StringBuilder();
		html.append(formHeader(questionIndex, QUESTION_RESPONSE));
		String question = "";
		try {
			question = rs.getString("question");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		html.append(question);
		html.append("<input type='text' name='answer'/>");
		html.append(formFooter());

		return html.toString();	
	}
	
	public void saveToDatabase(int quizID){
		switch(this.type){
		case FILL_IN_THE_BLANK:
			FillInTheBlankToDatabase(quizID);
			break;
		case MULTIPLE_CHOICE:
			MultipleChoiceToDatabase(quizID);
			break;
		case PICTURE_RESPONSE:
			PictureResponseToDatabase(quizID);
			break;
		case QUESTION_RESPONSE:
			QuestionResponseToDatabase(quizID);
			break;
		default:
			break;
		}
	}
	
	public void MultipleChoiceToDatabase(int quizID){
		DBConnection connection  = DBConnection.GetConnection(this.request);
		
		String choice1 = this.request.getParameter("choice1");
		String choice2 = this.request.getParameter("choice2");
		String choice3 = this.request.getParameter("choice3");
		String choice4 = this.request.getParameter("choice4");
		String answer = this.request.getParameter("radio");
		String question = this.request.getParameter("question");
		System.out.println("Quiz ID = " + quizID); 
		connection.executeQuery("INSERT INTO multiple_choice"
				+ "(quizID, question, answer, choice1, choice2, choice3, choice4)"
				+ " VALUES(\""+String.valueOf(quizID)+"\", \""+question+"\", \""+answer+"\", "
						+ "\""+choice1+"\", \""+choice2+"\", \""+choice3+"\", \""+choice4+"\")");

	}

	/**
	 * Creates a new fill in the blank question for the quiz being created
	 * @param request
	 */
	public void FillInTheBlankToDatabase(int quizID) {
		DBConnection connection  = DBConnection.GetConnection(this.request);

		String question = this.request.getParameter("question");
		String answer = this.request.getParameter("answer");
		
		String query = "INSERT INTO fill_in_the_blank"
				+ "(quizID, question, answer) VALUES("
				+ "\""+String.valueOf(quizID)+"\", \""+question+"\", \""+answer+"\")";
		
		connection.executeQuery(query);
	}

	/**
	 * Creates a new question-response question for the quiz being created
	 * @param request
	 */
	public void QuestionResponseToDatabase(int quizID) {
		DBConnection connection  = DBConnection.GetConnection(this.request);

		String question = this.request.getParameter("question");
		String answer = this.request.getParameter("answer");
		String query = "INSERT INTO question_response"
				+ "(quizID, question, answer) VALUES("
				+ "\""+String.valueOf(quizID)+"\", \""+question+"\", \""+answer+"\")";
		connection.executeQuery(query);
		
	}

	/**
	 * Creates a new picture-response question for the quiz being created
	 * @param request
	 */
	public void PictureResponseToDatabase(int quizID) {
		DBConnection connection  = DBConnection.GetConnection(this.request);

		String imageURL = this.request.getParameter("question");
		String answer = this.request.getParameter("answer");
		
		connection.executeQuery("INSERT INTO picture_response"
				+ "(quizID, imageURL, answer) VALUES("
				+ "\""+String.valueOf(quizID)+"\", \""+imageURL+"\", \""+answer+"\")");
	}
}
