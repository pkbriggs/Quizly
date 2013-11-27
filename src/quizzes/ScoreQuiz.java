package quizzes;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class QuizRunner
 */
@WebServlet("/ScoreQuiz")
public class ScoreQuiz extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScoreQuiz() {
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
		HttpSession session = request.getSession();
		boolean practice_mode = request.getParameter("practice_mode")!= null;
		
		String username = (String) session.getAttribute("username");
		Quiz quiz = (Quiz) session.getAttribute("curr_quiz");
		
		double score = quiz.scorePage(request);
		
		if(!quiz.finished()){
			RequestDispatcher dispatch = request.getRequestDispatcher("DisplayQuiz");
			dispatch.forward(request, response);
			return;
		}else{
			
			SuccessPage(request, response, quiz, practice_mode);		
			if(!practice_mode){
				RecordScore(request, username, quiz);
			}
		}
			
	}

	private void SuccessPage(HttpServletRequest request, HttpServletResponse response, Quiz quiz, boolean practice_mode){
		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(practice_mode){
			out.println("<h2>Practice Mode:</h2>");
		}
		out.println("You scored: " + quiz.getScore());
		out.println("It took you "+ GetTimeStr(request) + " to complete this quiz");
		out.println("Great Job!");
		
		if(!practice_mode){
			out.println("Your score has been recorded.");
		}
		
	}
	
	private void RecordScore(HttpServletRequest request, String username, Quiz quiz) {
		long time = GetTime(request);
		DBConnection connection= DBConnection.GetConnection(request);

		connection.executeQuery("INSERT INTO scores(username, quizID, score, time, dateTaken) "
				+ "VALUES(\""+username+"\", \""+quiz.getID()+"\", \""+quiz.getScore()+"\", \""+time+"\", \""+DBConnection.GetDate()+"\", ");
		
	}

	private String GetTimeStr(HttpServletRequest request){
		long time = GetTime(request);
		int minutes = (int) time / 60;
		long seconds = time;
		if(minutes > 0)
			seconds = time % minutes;
		
		return minutes + " minutes and " + seconds + " seconds";
	}
	
	private long GetTime(HttpServletRequest request){
		HttpSession session = request.getSession();
		long startTime = (Long) session.getAttribute("start_time");
		long endTime = System.currentTimeMillis();
		long time = (endTime - startTime)/1000;
		return time;
	}
}
