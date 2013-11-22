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
import javax.servlet.http.HttpSession;

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
		String quizID = request.getParameter("id");

		if(quizID != null){
			DBConnection connection = DBConnection.GetConnection(request);
			Quiz quiz = new Quiz(Integer.parseInt(quizID), connection);
			ArrayList<Question> questions = quiz.loadQuestionsFromDB(connection);
			
			//Set the session attribute
			HttpSession session = request.getSession();
			session.setAttribute("curr_quiz_questions", questions);
			
			PrintQuizToScreen(questions, connection, response);
			session.setAttribute("start_time", System.currentTimeMillis());
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
			html+=("<a href='DisplayQuiz?id="+quiz.getID()+"' >" +quiz.getTitle() + "</a><p>");
		}
		
		return html;
	}

	private void PrintQuizToScreen(ArrayList<Question> questions, DBConnection connection, HttpServletResponse response){
		PrintWriter out = null;
		
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.println("<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>");
		out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\""
				      + " \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		out.println("<html xmlns='http://www.w3.org/1999/xhtml'>");
		out.println("<head>");
		out.println("<script src='jquery.js'></script>");
		out.println("<title>"+PAGE_TITLE+"</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<form name='submit_quiz' action='ScoreQuiz' method='post'>");
		out.println(QuestionsToHTML(questions, connection));
		out.println("<input type='hidden' name='question' value='submit'>");
		out.println("<input type='submit' id='submit_button' value='Submit Quiz'/>");
		out.println("</form>");

	}
	
	private String QuestionsToHTML(ArrayList<Question> questions, DBConnection connection){
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
		System.out.println(html);
		return html;
	}
	
	/**
	 * Returns the html information that begins each question's form
	 * @param questionIndex
	 * @param type
	 * @return
	 */
	private String questionHeader(int type, int questionID){
		String html = "";
		html+="<div name= 'question"+questionID+"' class='question'>";
		html+="<p>" + questionID + ") ";
		return html;
	}
	
	private String questionFooter(){
		return "</div><p>";
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
		html.append(questionHeader(Question.QUESTION_RESPONSE, question.getQuestionID()));
		
		String questionStr =question.getQuestion();
		questionStr = questionStr.replaceAll(" _+", "<input type='text' name='answer"+question.getQuestionID()+"'/>");
		
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
		html.append(questionHeader(Question.MULTIPLE_CHOICE, question.getQuestionID()));
		String questionStr= question.getQuestion();
		ArrayList<String> choices = question.getChoices();
		String choice1 = choices.get(0);
		String choice2 = choices.get(1);
		String choice3 = choices.get(2);
		String choice4 = choices.get(3);

		html.append(questionStr + "<p>");
		html.append("<input type='radio' name='radio"+question.getQuestionID()+"' value='"+choice1+"'/>"+choice1+"<p>" );
		html.append("<input type='radio' name='radio"+question.getQuestionID()+"' value='"+choice2+"'/>"+choice2+"<p>" );
		html.append("<input type='radio' name='radio"+question.getQuestionID()+"' value='"+choice3+"'/>"+choice3+"<p>" );
		html.append("<input type='radio' name='radio"+question.getQuestionID()+"' value='"+choice4+"'/>"+choice4+"<p>" );
		
		html.append(questionFooter());
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
		html.append(questionHeader(Question.PICTURE_RESPONSE, question.getQuestionID()));
		String imageURL = question.getQuestion();
		html.append("<img src='"+imageURL+"'/>");
		html.append("<input type='text' name='answer"+question.getQuestionID()+"'/>");
		html.append(questionFooter());

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
		html.append(questionHeader(Question.QUESTION_RESPONSE, question.getQuestionID()));
		String questionStr = question.getQuestion();
		html.append(questionStr);
		html.append("<input type='text' name='answer"+question.getQuestionID()+"'/>");
		html.append(questionFooter());

		return html.toString();	
	}
}
