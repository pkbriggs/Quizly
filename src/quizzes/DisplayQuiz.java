package quizzes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnection.DBConnection;

/**
 * Servlet implementation class TakeQuiz
 */
@WebServlet("/DisplayQuiz")
public class DisplayQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String PAGE_TITLE = "Good luck with this Doozy!";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("In the get");
		String quizID = request.getParameter("id");
		System.out.println("id="+ quizID);

		if(quizID != null){
			DBConnection connection = DBConnection.GetConnection(request);
			Quiz quiz = new Quiz(Integer.parseInt(quizID), connection);
			PrintQuizToScreen(quiz, connection, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formID = request.getParameter("formID");

		if(formID != null && formID.equals("list_quizzes")){
			PrintWriter out = response.getWriter();
			
			String outStr = listQuizzes(request);//Quiz.quizzesToHTML("SELECT * FROM quizzes" , request);
			out.println(outStr);	
			return;
		}

	}
	

	private String listQuizzes(HttpServletRequest request){
		DBConnection connection = DBConnection.GetConnection(request);
		ArrayList<Quiz> quizzes = Quiz.GetArrayOfQuizzes("SELECT * FROM quizzes", connection);
		String html = "";
		for(Quiz quiz: quizzes){
			html+=("<a href='DisplayQuiz?id="+quiz.getID()+"' >" +quiz.getTitle() + " HEERE</a><p>");
		}
		
		return html;
	}

	private void PrintQuizToScreen(Quiz quiz, DBConnection connection, HttpServletResponse response){
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintHeader(out);
		out.println("<body>");
		out.println(QuestionsToHTML(quiz, connection));
		out.println("<form method='post' action='QuizRunner'>");
		out.println("<input type='hidden' name='question' value='submit'>");
		out.println("<input type='submit' value='Submit Quiz'/>");
		out.println("</form>");

	}
	
	private String QuestionsToHTML(Quiz quiz, DBConnection connection){
		ArrayList<Question> questions = quiz.loadQuestionsFromDB(connection);
		System.out.println("Numquestions = " +questions.size());
		String html = "";
		for(Question question: questions){
			int type = question.getType();
			switch(type){
			case Question.MULTIPLE_CHOICE:
				html+= MultipleChoiceToHtml((MultipleChoice)question);
				break;
			case Question.FILL_IN_THE_BLANK:
				html+= FillInTheBlankToHtml((FillInTheBlank)question);
				break;
			case Question.PICTURE_RESPONSE:
				html+= PictureResponseToHtml((PictureResponse)question);
				break;
			case Question.QUESTION_RESPONSE:
				html+= QuestionResponseToHtml((QuestionResponse)question);
				break;
			}
		}
		
		return html;
	}
	/**
	 * Prints the html between the <head> and </head> tags for the output page
	 * @param out
	 */
	private void PrintHeader(PrintWriter out){
		out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\""
				      + " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("<head>");
		out.println("<title>"+PAGE_TITLE+"</title>");
		out.println("</head>");
	}
	
	
	/**
	 * Returns the html information that begins each question's form
	 * @param questionIndex
	 * @param type
	 * @return
	 */
	private String formHeader(int type, int questionID){
		String html = "";
		html+="<form action='QuizRunner' method='post'>";
		html+="<input type='hidden' name='question' value='"+questionID+"'/>";
		html+="<input type='hidden' name='type' value='"+type+"'/>";
		html+="<p>" + questionID + ") ";
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
	private String FillInTheBlankToHtml(FillInTheBlank question){
		StringBuilder html = new StringBuilder();
		html.append(formHeader(Question.QUESTION_RESPONSE, question.getQuestionID()));
		String questionStr =question.getQuestion();
		System.out.println("question: " + question);
		questionStr = questionStr.replaceAll(" _+", "<input type='text' name='answer'/>");
		
		html.append(questionStr);
		return html.toString();
	}
	

	/**
	 * Takes the information about a multiple choice question 
	 * from the row the result set @rs is pointing, and creates an
	 * html representation of that question.
	 * @param rs
	 * @return
	 */
	private String MultipleChoiceToHtml(MultipleChoice question){
		StringBuilder html = new StringBuilder();
		html.append(formHeader(Question.MULTIPLE_CHOICE, question.getQuestionID()));
		String questionStr= question.getQuestion();
		ArrayList<String> choices = question.getChoices();
		String choice1 = choices.get(0);
		String choice2 = choices.get(1);
		String choice3 = choices.get(2);
		String choice4 = choices.get(3);

		html.append(questionStr + "<p>");
		html.append("<input type='radio' value='"+choice1+"'/>"+choice1+"<p>" );
		html.append("<input type='radio' value='"+choice2+"'/>"+choice2+"<p>" );
		html.append("<input type='radio' value='"+choice3+"'/>"+choice3+"<p>" );
		html.append("<input type='radio' value='"+choice4+"'/>"+choice4+"<p>" );
		
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
	private String PictureResponseToHtml(PictureResponse question){
		StringBuilder html = new StringBuilder();
		html.append(formHeader(Question.PICTURE_RESPONSE, question.getQuestionID()));
		String imageURL = question.getQuestion();
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
	private String QuestionResponseToHtml(QuestionResponse question){
		StringBuilder html = new StringBuilder();
		html.append(formHeader(Question.QUESTION_RESPONSE, question.getQuestionID()));
		String questionStr = question.getQuestion();
		html.append(questionStr);
		html.append("<input type='text' name='answer'/>");
		html.append(formFooter());

		return html.toString();	
	}
}
