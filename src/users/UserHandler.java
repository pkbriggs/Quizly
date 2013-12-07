package users;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UserHandler
 */
@WebServlet("/UserHandler")
public class UserHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserHandler() {
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
		
		if (requestType.equals("PROFPIC")) {
			String username = URLDecoder.decode(request.getParameter("username").replace("+", "%2B"), "UTF-8").replace("%2B", "+"); // http://stackoverflow.com/questions/2632175/java-decoding-uri-query-string
			User u = User.getUserFromID(User.getIDFromUsername(username));
			String photoFilename = u.getPhotoFilename();
			if (photoFilename == null)
				photoFilename = "http://blogs.utexas.edu/bonnecazegroup/files/2013/02/blank-profile-hi.png";
			out.println(String.format("<img src='%s' class='small-profile-picture' />", photoFilename));
		} else if (requestType.equals("SETPROFPIC")) {
			String url = request.getParameter("url");
			User.setProfilePicture(User.getID(request.getSession()), url);
			
			out.println(String.format("<img src='%s' class='medium-profile-picture' />", url));
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
