package messages;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quizzes.Quiz;
import users.User;

/**
 * Servlet implementation class ChallengeHandler
 */
@WebServlet("/ChallengeHandler")
public class ChallengeHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChallengeHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestType = request.getParameter("type");
		
		PrintWriter out = response.getWriter(); 
		response.setContentType("text/html"); 
		
		if (requestType.equals("NEW")) { // send a new challenge!
			int id = Integer.parseInt(request.getParameter("id"));
			String username = User.getUsernameFromID(id);
			String challengedUsername = request.getParameter("challengedUsername");
			int quizId = Integer.parseInt(request.getParameter("quizid"));
			String quizName = Quiz.getQuiz(quizId).getTitle();
			
			Challenge.challengeUser(username, challengedUsername, quizId, quizName);
			
			out.println("ok");
		} else if (requestType.equals("ACCEPT") || requestType.equals("REJECT")) {
			int challengeid = Integer.parseInt(request.getParameter("challengeid"));
			Challenge.acceptChallenge(challengeid);
			
			out.println("ok");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
