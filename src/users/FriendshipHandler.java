package users;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FriendshipHandler
 */
@WebServlet("/FriendshipHandler")
public class FriendshipHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FriendshipHandler() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userOne = Integer.parseInt(request.getParameter("user1"));
		int userTwo = Integer.parseInt(request.getParameter("user2"));
		String type = request.getParameter("type");
		
		PrintWriter out = response.getWriter(); 
		
		if (type.equals("REQUEST")) {
			Friendship.sendFriendRequest(userOne, userTwo);
			out.println("ok");
		} else if (type.equals("ACCEPT")) {
			Friendship.acceptFriendRequest(userOne, userTwo);
			out.println("accept success");
		} else if (type.equals("REJECT")) {
			Friendship.rejectFriendRequest(userOne, userTwo);
			out.println("reject success");
		} else if (type.equals("REMOVE")) {
			Friendship.removeFriend(userOne, userTwo);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
