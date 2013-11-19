package quizzes;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dbconnection.DBConnection;

/**
 * Servlet implementation class CreateQuiz
 */
@WebServlet("/CreateQuiz")
public class CreateQuiz extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateQuiz() {
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
		String formID = request.getParameter("formID");
		ServletContext context = request.getServletContext();
		DBConnection connection = (DBConnection) context.getAttribute("dbconnection");
		if(formID.equals("initialize_quiz")){
			InitializeQuiz(request, connection);
		}
		
		HttpSession session = request.getSession();
		Quiz currQuiz = (Quiz) session.getAttribute("quiz_being_created");
		
		if(formID.equals("submit_quiz")){
			AddQuizToDatabase(request, connection);
			RequestDispatcher dispatch = request.getRequestDispatcher("TesterFile.jsp");
			dispatch.forward(request, response);
			return;
		}
		if(formID.equals("multiple_choice")){
			Question question = new Question(request, Question.MULTIPLE_CHOICE);
			currQuiz.addQuestion(question);
		}
		if(formID.equals("fill_in_the_blank")){
			Question question = new Question(request, Question.FILL_IN_THE_BLANK);
			currQuiz.addQuestion(question);
		}
		if(formID.equals("question_response")){
			Question question = new Question(request, Question.QUESTION_RESPONSE);
			currQuiz.addQuestion(question);
		}
		if(formID.equals("picture_response")){
			Question question = new Question(request, Question.PICTURE_RESPONSE);
			currQuiz.addQuestion(question);
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("CreateQuiz.jsp");
		dispatch.forward(request, response);
	}
	
	/**
	 * After finishing the quiz, this adds the quiz to the database
	 * @param request
	 */
	private void AddQuizToDatabase(HttpServletRequest request, DBConnection connection) {
		Quiz quiz = (Quiz) DBConnection.GetSessionAttribute(request, "quiz_being_created");
		String description = request.getParameter("description");
		String title = request.getParameter("title");

		quiz.setDescription(description);
		quiz.setTitle(title);
		//TODO!! Store the creatorID as well once we have a login page
		quiz.updateQuizInDB(connection);
		
	}

	
	/**
	 * Creates a new quiz in the database, ready and prepared to be edited
	 * and filled out with questions. 
	 * @param request
	 * @param connection
	 */
	private void InitializeQuiz(HttpServletRequest request, DBConnection connection){
		Quiz newQuiz = new Quiz();
		HttpSession session = request.getSession();
		session.setAttribute("quiz_being_created", newQuiz);
	}

}
