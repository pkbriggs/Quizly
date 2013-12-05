package users;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		}
		else if(request.getParameter("admin-see-stats") != null){
			//format Quiz.getStats() - returns array of ints
		}
		else if(request.getParameter("admin-clear-hist") != null){
			//get from quizHistory page
			//int quizID = Integer.parseInt(request.getParameter("quizID"));
		}
		else{
			//redirect
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
