package dbconnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import quizzes.Quiz;
import users.User;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Search() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		List<User> userList = User.search(query);
//		List<Quiz> quizList = 
		
		PrintWriter out = response.getWriter(); 
		response.setContentType("text/html"); 
		
//		out.println("<ul class='search-results'>");
		
		for (User user: userList) {
			// the bolded username is done by simply bolding the characters that match the user's search so far. because
			// when the user search is done there is only a wildcard at the end, this means that if the user's query is
			// N character long, the first N characters of the returned strings should be bolded
			String username = user.getUsername();
			String boldedUsername = "<b>" + username.substring(0, query.length()) + "</b>" + username.substring(query.length());
			
			out.println("<li class='search-result search-result-user'>");
			out.println(String.format("<a href='/Quizly/profile.jsp?id=%d'>%s</a>", user.getID(), boldedUsername));
			out.println("</li>");
		}
		
//		out.println("</ul>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
