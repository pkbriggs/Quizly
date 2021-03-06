package quizzes;

import java.io.IOException;
import java.io.PrintWriter;
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

import achievements.Achievement;
import users.User;
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
		String formID = request.getParameter("formID");
		if(formID.equals("initialize_quiz"))
			InitializeQuiz(request);
		
		RequestDispatcher dispatch = request.getRequestDispatcher("CreateQuiz.jsp");
		dispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String formID = request.getParameter("formID");

		HttpSession session = request.getSession();
		Quiz currQuiz = (Quiz) session.getAttribute("quiz_being_created");
		
		if(currQuiz ==null)
			System.out.println("ERROR occured somewhere. Quiz not initialized");
		
		if(formID.equals("submit_quiz")){
			AddQuizToDatabase(request);
			String username = User.getUsername(session);
			
			String achievement = Achievement.CheckForCreatingQuizAchievements(username);
			RequestDispatcher dispatch ;
			if(achievement != null){
				request.setAttribute("achievement", achievement);
				dispatch = request.getRequestDispatcher("achievement.jsp");
			}else{
				dispatch = request.getRequestDispatcher("index.jsp");
			}
			
			dispatch.forward(request, response);
			return;
		}
		try{
			if(formID.equals("multiple_choice")){
				System.out.println("Before making question " + request);
				Question question = new MultipleChoice(request);
				System.out.println("After making question");
				currQuiz.addQuestion(question);
			}
			if(formID.equals("fill_in_the_blank")){
				Question question = new FillInTheBlank(request);
				currQuiz.addQuestion(question);
			}
			if(formID.equals("question_response")){
				Question question = new QuestionResponse(request);
				currQuiz.addQuestion(question);
			}
			if(formID.equals("picture_response")){
				Question question = new PictureResponse(request);
				currQuiz.addQuestion(question);
			}
		}catch(Exception e){
			request.setAttribute("error_message", e.getMessage());
			RequestDispatcher dispatch = request.getRequestDispatcher("ErrorPage.jsp");
			System.out.println("this is the error message that should have been recieved: "+ e.getMessage());
			dispatch.forward(request, response);
			return;
		}
		
		RequestDispatcher dispatch = request.getRequestDispatcher("CreateQuiz.jsp");
		dispatch.forward(request, response);
	}
	
	/**
	 * After finishing the quiz, this adds the quiz to the database
	 * @param request
	 */
	private void AddQuizToDatabase(HttpServletRequest request) {
		Quiz quiz = (Quiz) DBConnection.GetSessionAttribute(request, "quiz_being_created");
		String description = request.getParameter("description");
		String title = request.getParameter("title");
		HttpSession session = request.getSession();
		String username = User.getUsername(session);
		
		if(request.getParameter("multiple_pages") != null){
			int questions_per_page = Integer.parseInt(request.getParameter("questions_per_page")); 
			quiz.setNumPagesFromNumQuestions(questions_per_page);
		}else{
			quiz.setNumPages(1);
		}
		
		if(request.getParameter("randomize") != null){
			quiz.setRandomization(DBConnection.TRUE);
		}else{
			quiz.setRandomization(DBConnection.FALSE);
		}
		
		quiz.setDescription(description);
		quiz.setTitle(title);
		quiz.setCreator(username);
		quiz.updateQuizInDB();		
	}

	/**
	 * Creates a new quiz in the database, ready and prepared to be edited
	 * and filled out with questions. 
	 * @param request
	 * @param connection
	 */
	private void InitializeQuiz(HttpServletRequest request){
		System.out.println("Initializing the quiz!!");
		Quiz newQuiz = new Quiz();
		HttpSession session = request.getSession();
		session.setAttribute("quiz_being_created", newQuiz);
	}

}
