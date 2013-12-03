package quizzes;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
    private static final String PAGE_TITLE = "Take Quiz";
    public static final String HEADER = "<%@ page language=\"java\" contentType=\"text/html;"
    		+ " charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\"%>"
    		+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">"
    		+ "<jsp:include page=\"/helpers/boilerplate.jsp\"> " 
    + "<jsp:param name=\"pageTitle\" value=\"Quizly\"/>"
    + "<jsp:param name=\"cssInclude\" value=\"css/index-page.css\" />"
    + "</jsp:include><%@ include file=\"helpers/navbar.jsp\" %>";
    
    public static final String FOOTER = "<%@ include file=\"helpers/end_boilerplate.jsp\" %>";
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

		//If this is from a link wanting to initialize the quiz
		if(quizID != null){
			HttpSession session = request.getSession();
			
			Quiz quiz = new Quiz(Integer.parseInt(quizID));
			quiz.setStartTime();
			
			session.setAttribute("curr_quiz", quiz);
			
			//Forward the request to print the next page
			RequestDispatcher dispatch = request.getRequestDispatcher("DisplayQuizPage.jsp");
			dispatch.forward(request, response);
		
		} else{
			PrintWriter out = null ;
			try {
				out = response.getWriter();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			out.append("Error: please make sure you are calling DisplayQuiz correctly from the form.<br>"
					+ "Please include a parameter called 'id' passed through the GET with value of the quiz"
					+ "you want to display.");
		}
	}

/*
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("This never should have been reached!");

	}
	
}
