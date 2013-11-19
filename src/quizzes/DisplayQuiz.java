package quizzes;

import java.io.IOException;
import java.io.PrintWriter;
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
			PrintQuizToScreen(connection, response, quiz);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formID = request.getParameter("formID");
		System.out.println("Made it to ");

		if(formID != null && formID.equals("list_quizzes")){
			System.out.println("Im thisblock/");
			PrintWriter out = response.getWriter();
			String outStr = Quiz.quizzesToHTML("SELECT * FROM quizzes" , request);
			out.println(outStr);	
			return;
		}

	}
	


	private void PrintQuizToScreen(DBConnection connection, HttpServletResponse response, Quiz quiz){
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintHeader(out);
		out.println("<body>");
		String questions = quiz.QuestionsToHTML(connection);
		out.append(questions);
		System.out.println("Questions html =" + questions );
		out.append("<form method='post' action='QuizRunner'>");
		out.append("<input type='hidden' name='question' value='submit'>");
		out.append("<input type='submit' value='Submit Quiz'/>");
		out.append("</form>");

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
}
