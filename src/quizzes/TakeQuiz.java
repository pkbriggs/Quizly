package quizzes;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dbconnection.DBConnection;

/**
 * Servlet implementation class TakeQuiz
 */
@WebServlet("/TakeQuiz")
public class TakeQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String PAGE_TITLE = "Good luck with this Doozy!";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TakeQuiz() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer quizID = Integer.parseInt( request.getParameter("quiz_id") );
		DBConnection connection = DBConnection.GetConnection(request);
		Quiz quiz = new Quiz(quizID, connection);
		PrintQuizToScreen(response, quiz);
	}

	private void PrintQuizToScreen(HttpServletResponse response, Quiz quiz){
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintHeader(out);
		out.println("<body>");
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
