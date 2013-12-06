package messages;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import users.User;

/**
 * Servlet implementation class MessageHandler
 */
@WebServlet("/MessageHandler")
public class MessageHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MessageHandler() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    private void writeConversationHtml(String currentUser, String otherUser, PrintWriter out) {
    	// get all the conversations between the current user and the provided username
		List<Message> conversation = Message.getConversation(currentUser, otherUser);
		
		// generate the html
		for (Message msg: conversation) {
			User user = User.getUserFromUsername(msg.username);
			String photoFilename = user.getPhotoFilename();
			if (photoFilename == null)
				photoFilename = "http://blogs.utexas.edu/bonnecazegroup/files/2013/02/blank-profile-hi.png";
			
			out.println("<li>");
			out.println("<p>");
			out.println(String.format("<img src='%s' class='xsmall-profile-picture' />", photoFilename));
			out.println(String.format("<a href='/Quizly/profile.jsp?id=%d'>%s</a>", User.getIDFromUsername(msg.username), msg.username));
			out.println("</p>");
			out.println("<p>"+msg.message+"</p>");
			out.println("</li>");
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestType = request.getParameter("type");
		int otherID = Integer.parseInt(request.getParameter("otherID"));
		String otherUser = User.getUsernameFromID(otherID);
		String currentUser = User.getUsername(request.getSession());
		
		PrintWriter out = response.getWriter(); 
		response.setContentType("text/html"); 
		
		if (requestType.equals("LIST")) {
			writeConversationHtml(currentUser, otherUser, out);
		} else if (requestType.equals("SEND")) { // send a message to the provided username
			String message = request.getParameter("message");
			Message.sendMessage(currentUser, otherUser, message, null);
			writeConversationHtml(currentUser, otherUser, out);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
