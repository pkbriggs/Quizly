package users;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import announcements.Announcement;
import quizzes.Quiz;

/**
 * Servlet implementation class Admin
 */
@WebServlet("/Admin")
public class Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Admin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("admin-promote") != null){
			int userID = Integer.parseInt(request.getParameter("userID"));
			User.promoteUserToAdmin(userID);
		}
		else if(request.getParameter("admin-demote") != null){
			int userID = Integer.parseInt(request.getParameter("userID"));
			User.demoteUserFromAdmin(userID);
		}
		else if(request.getParameter("admin-delete") != null){
			int userID = Integer.parseInt(request.getParameter("userID"));
			User u = User.getUserFromID(userID);
			User.removeUser(u);
		}
		else if(request.getParameter("admin-quiz-delete")!= null) {
			int quizID = Integer.parseInt(request.getParameter("quizID"));
			Quiz.delete(quizID);
		}
		else if(request.getParameter("admin-announce") != null){
			//redirect to announcement form
			int userID = Integer.parseInt(request.getParameter("userID"));
			User user = User.getUserFromID(userID);
			Announcement.newAnnouncement(user, request.getParameter("subject"), request.getParameter("body"));
		}
		else if(request.getParameter("admin-see-stats") != null){
			//returns list of stats in this order: numUsers, numQuizzes, numFriendships
			ArrayList<Integer> stats = Quiz.getStats();
		}
		else if(request.getParameter("admin-clear-hist") != null){
			int quizID = Integer.parseInt(request.getParameter("quizID"));
			Quiz.deleteQuizHistory(quizID);
		}
		else{
			//redirect
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("admin.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
