package quizzes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		ArrayList<Question> questions = (ArrayList<Question>) session.getAttribute("curr_quiz_questions");
		
		int numCorrect = 0;
		for(int i = 0; i< questions.size(); i++){
			numCorrect += ScoreQuestion(request, questions.get(i));
		}
		double score = (double) numCorrect / questions.size();
		
		long seconds = GetTime(request);
		System.out.println("num seconds:"+ seconds);
		
		int minutes = (int) seconds / 60;
		System.out.println("num minutes:"+ minutes);
		
		if(minutes > 0)
			seconds = seconds % minutes;
		
		PrintWriter out = response.getWriter();
		out.println("You scored: " + score);
		out.println("It took you "+ minutes + " minutes and " + seconds+ " seconds to complete this quiz");
		out.println("Great Job!");

	}
	
	private long GetTime(HttpServletRequest request){
		HttpSession session = request.getSession();
		long startTime = (Long) session.getAttribute("start_time");
		long endTime = System.currentTimeMillis();
		return (endTime - startTime)/1000;
	}
	
	private int ScoreQuestion(HttpServletRequest request, Question question){
		int type = question.getType();
		int questionID = question.getQuestionID();
		String response = "";
		System.out.println("Scoring question =" + question.getQuestion());
		
		if(type == Question.MULTIPLE_CHOICE){
			response = request.getParameter("radio"+questionID);
		}
		else{
			response = request.getParameter("answer"+questionID);
		}
		
		int score =  (question.isCorrect(response)) ? 1 : 0;
		System.out.println("Score: "+ score);
		return score;
	}
}
