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

import users.User;
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
		
		String username = User.getUsername(session);
		Quiz quiz = (Quiz) session.getAttribute("curr_quiz");

		if(quiz== null){
			System.out.println("dude wtf?");
		}
		
		boolean practice_mode = quiz.isPracticeMode(request);
		
		quiz.scorePage(request);
		
		if(!quiz.finished()){
			RequestDispatcher dispatch = request.getRequestDispatcher("DisplayQuizPage.jsp");
			dispatch.forward(request, response);
			return;
		}else{
				
			if(!practice_mode){
				RecordScore(request, username, quiz);
			}
			
			RequestDispatcher dispatch = request.getRequestDispatcher("QuizSuccessPage.jsp");
			dispatch.forward(request, response);
		}
			
	}


	
	private void RecordScore(HttpServletRequest request, String username, Quiz quiz) {
		long time = quiz.getTime();
		double score = (double) quiz.getPoints() /quiz.getTotalPoints();
		DBConnection connection= DBConnection.getInstance();
		String query = "INSERT INTO scores (username, quizID, score, time, dateTaken) "
				+ "VALUES(\""+username+"\", \""+quiz.getID()+"\", \""+score+"\", \""+time+"\", \""+DBConnection.GetDate()+"\")";
		connection.executeQuery(query);
		System.out.println("Just did query:" +query);
	}

}
