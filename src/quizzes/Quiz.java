package quizzes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import users.User;

import com.mysql.jdbc.PreparedStatement;

import dbconnection.DBConnection;

/*
 * This class will store a series of questions of various types that 
 * can then 
 */
public class Quiz{
	
	private String title;
	private String description;
	private ArrayList<Question> questions;
	private int numCorrect;
	private int numPages;
	private int currPage;
	private String creator;
	private int randomize;
	private boolean practice_mode;
	private long startTime;
	private long endTime;
	private int points;
	private int totalPoints;

	private int id;
	private String dateCreated;
	private boolean inDatabase;
	//TODO add the user info
	
	public static Quiz getQuiz(int id){
		return new Quiz(id);
	}
	
	public Quiz(){
		this.title = "";
		this.description = "";
		this.dateCreated = DBConnection.GetDate();
		this.id= -1;
		this.inDatabase = false;
		this.numCorrect = 0;
		this.currPage = 0;
		this.creator = "";
		this.randomize = DBConnection.FALSE;
		this.practice_mode = false;
		this.startTime = 0;
		this.endTime = 0;
		questions = new ArrayList<Question>();
	}
	
	/*This constructor retrieves information from the database for the quizID
	 * provided and creates a new quiz object from that information
	 */
	public Quiz(int id){
		DBConnection connection = DBConnection.getInstance();
		ResultSet rs = connection.executeQuery("SELECT * FROM quizzes WHERE id="+id);
		try {
			rs.absolute(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FillWithInfoFromRow(this, rs);
		this.inDatabase = true;
		this.numCorrect = 0;
		this.questions = loadQuestionsFromDB();
		this.currPage = 0;
		this.practice_mode = false;
		this.startTime = 0;
		this.endTime= 0;
	}
	
	private int getNumQuestionsPerPage(){
		int numQuestions = this.questions.size();
		System.out.println("Numquestions: "+ numQuestions + " this.numPages = " + this.numPages);
		int remainder = (numQuestions % this.numPages == 0) ? 0 : 1;
		if(this.numPages == 0)
			return 1;
		else
			return ((int)numQuestions / this.numPages) + remainder;
	}
	/**
	 * Gets all the quiz info from the row the result set is currently pointing to 
	 * sets @quiz information information to the information in that row.
	 * @param quiz, rs, row
	 * @param row
	 */
	private static void FillWithInfoFromRow(Quiz quiz, ResultSet rs){
		try {
			quiz.setTitle(rs.getString("title"));
			quiz.setDescription(rs.getString("description"));
			quiz.setDateCreated(rs.getString("dateCreated"));
			quiz.setID(rs.getInt("id"));
			quiz.setNumPages( rs.getInt("numPages"));
			quiz.setCreator(rs.getString("creator"));
			quiz.setRandomization(rs.getInt("randomize"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void setRandomization(int randomize) {
		this.randomize = randomize;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}


	/**
	 * Sets this object's id to @id
	 * @param id
	 */
	private void setID(int id){
		this.id = id;
	}
	
	public int numQuestions(){
		return this.questions.size();
	}
	
	/**
	 * Sets the dateCreated of this quiz object to @date
	 * @param date
	 */
	private void setDateCreated(String date){
		this.dateCreated = date;
	}
	
	/**
	 * Stores the initial quiz in the database and returns the autogenerated ID
	 * @param connection
	 * @return
	 */
	private int initializeQuizToDatabase(){
		DBConnection connection = DBConnection.getInstance();
		ResultSet rs = connection.executeQuery("INSERT INTO quizzes(title, dateCreated) values(\""+this.title+"\" , \""+this.dateCreated+"\")");
		int id = 0;
		try {
			//Get the autogenerated quiz id
			rs.next();
			id = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return id;
	}
	
	/**
	 * Sets the description of this quiz
	 * @param description
	 */
	public void setDescription(String description){
		this.description = description;
	}
	
	/**
	 * Returns the description associated with this quiz
	 * @return String description
	 */
	public String getDescription(){
		return description;
	}
	
	/**
	 * Adds a question to the list of questions 
	 * @param QuestionOld question
	 */
	public void addQuestion(Question question){
		System.out.println("Questions: "+ questions.toString());
		System.out.println("Question: "+ question.toString());
		questions.add(question);
	}
	
	/**
	 * Returns the title of this quiz object
	 * @return
	 */
	public String getTitle(){
		return this.title;
	}
	
	/**
	 * sets the title of this quiz object
	 * @param
	 */
	public void setTitle(String title){
		this.title = title;
	}
	
	/**
	 * sets the title of this quiz object
	 * @param
	 */
	public int numPages(){
		return this.numPages;
	}
	
	/**
	 * Returns the id of this quiz object
	 * @return 
	 */
	public int getID(){
		return this.id;
	}
	
	/**
	 * Stores this quizzes information in the database
	 * @param connection
	 */
	public void updateQuizInDB(){
		DBConnection connection = DBConnection.getInstance();

		//TODO update the creator as well
		if(!this.inDatabase){
			this.id = this.initializeQuizToDatabase();
			this.inDatabase = true;
		}
		connection.executeQuery("UPDATE quizzes SET title='"+this.title+"', description='"+this.description+"' "
				+ ", numPages='"+this.numPages+"' , dateCreated='"+this.dateCreated+"', creator='"+this.creator +"', randomize='"+this.randomize +"' WHERE id=" + this.id);
		saveQuestionsToDatabase(this.id);
	}
	
	/**
	 * finds all the questions associated with the quiz in the database,
	 * makes Question objects out of them, and adds them to the quizzes 
	 * array of questions so they can be printed
	 */
	private ArrayList<Question> loadQuestionsFromDB(){
		DBConnection connection = DBConnection.getInstance();
		ArrayList<Question> questions = new ArrayList<Question>();
		
		//Multiple Choice Questions
		ResultSet rs = connection.executeQuery("SELECT * FROM multiple_choice WHERE quizID="+this.id);
		AddQuestions(rs, questions, Question.MULTIPLE_CHOICE);
		
		//Fill in the blank questions
		rs = connection.executeQuery("SELECT * FROM fill_in_the_blank WHERE quizID="+this.id);
		AddQuestions(rs, questions, Question.FILL_IN_THE_BLANK);
		
		//Add the question response questions
		rs = connection.executeQuery("SELECT * FROM question_response WHERE quizID="+this.id);
		AddQuestions(rs, questions, Question.QUESTION_RESPONSE);
		
		//Add the picture-response questions
		rs = connection.executeQuery("SELECT * FROM picture_response WHERE quizID="+this.id);
		AddQuestions(rs, questions, Question.PICTURE_RESPONSE);
		
		this.questions = questions;
		
		if(this.randomize == DBConnection.TRUE)
			Collections.shuffle(questions);
		return questions;
	}
	
	private void AddQuestions(ResultSet rs, ArrayList<Question> questions,
			int type) {
		Question question  = null;
		try {
			while(rs.next()){
				switch(type){
				case Question.MULTIPLE_CHOICE:
					question = new MultipleChoice(rs, questions.size());
					break;
				case Question.FILL_IN_THE_BLANK:
					question = new FillInTheBlank(rs, questions.size());
					break;
				case Question.PICTURE_RESPONSE:
					question = new PictureResponse(rs, questions.size());
					break;
				case Question.QUESTION_RESPONSE:
					question = new QuestionResponse(rs, questions.size());
					break;
				}
				questions.add(question);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * A static method that returns an array of quizzes using the information
	 * from the database according to the query provided
	 * @param query, connection
	 * @return
	 */
	public static ArrayList<Quiz> GetArrayOfQuizzes(String query){
		DBConnection connection = DBConnection.getInstance();
		ResultSet rs = connection.executeQuery(query);
		ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
		try {
			while(rs.next()){
				Quiz quiz = new Quiz();
				FillWithInfoFromRow(quiz, rs);
				System.out.println("After filling with Info: quizID=" + quiz.getID() + " quizTitle= "+ quiz.getTitle()  +  " datecreated = "+ quiz.dateCreated);
				quizzes.add(quiz);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return quizzes;
	}
	
	
	private void saveQuestionsToDatabase(int quizID){

		for(Question question: this.questions){
			System.out.println("Saving this question: " + question.toString());
			question.setQuizID(quizID);
			question.saveToDatabase();
		}
	}
	
	public ArrayList<Question> getAllQuestions(){
		return this.questions;
	}
		
	public double getPoints(){
		return this.points;
	}
	
	public double getTotalPoints(){
		return this.totalPoints;
	}

	public void scorePage(HttpServletRequest request){
		for(int i = getStartIndex(); i< getEndIndex(); i++){
			this.points += this.questions.get(i).score(request);
			this.totalPoints += this.questions.get(i).numAnswers();
		}
		
		//once a page is scored, go to the next page
		this.currPage++;
	}
	
	private int getEndIndex() {
		int end = (this.currPage+1)*getNumQuestionsPerPage() ;
		end = (end > this.questions.size()) ? this.questions.size(): end;
		System.out.println("returning endIndex: "+ end);
		return end;
	}

	private int getStartIndex() {
		int start = this.currPage * getNumQuestionsPerPage();
		System.out.println("returning startIndex: "+ start);
		return start;
	}

	public void setNumPages(int num_pages) {
		this.numPages = num_pages;
		System.out.println("Just set numpages to : "+ this.numPages);
	}
	
	public void setNumPagesFromNumQuestions(int questions_per_page){
		if(this.questions.size() % questions_per_page == 0){
			this.numPages= this.questions.size() / questions_per_page;
		}else{
			this.numPages = (this.questions.size() / questions_per_page) + 1;
		}
		System.out.println("Just set numpages from questions to : "+ this.numPages);
	}
	
	public ArrayList<Question> getPageQuestions(){
		ArrayList<Question> page = new ArrayList<Question>();
		for(int i = getStartIndex(); i< getEndIndex(); i++){
			page.add(questions.get(i));
		}
		return page;
		
	}

	public int getCurrPage() {
		return this.currPage;
	}
	
	public boolean finished(){
		return this.currPage == this.numPages;
	}

	public boolean isLastPage() {
		return this.currPage == this.numPages - 1 ;
	}

	public static String listQuizzes(String query){
		ArrayList<Quiz> quizzes = Quiz.GetArrayOfQuizzes(query);
		String html = "";
		for(Quiz quiz: quizzes){
			html+=("<a href='DisplayQuiz?id="+quiz.getID()+"' >" +quiz.getTitle() + "</a><p>");
		}
		
		return html;
	}
	
	public String getCreator(){
		return this.creator;
	}
	
	public String getDateCreated(){
		return this.dateCreated;
	}
	
	@Override
	public String toString(){
		if(this.questions == null){
			return "questions == null";
		}
		return "Title: "+ this.title + " Creator: " + this.creator + " numQuestions:" + questions.size();
	}
	
	/**
	 * returns whether this quiz should be recorded in the database or not
	 * @param request
	 * @return
	 */
	public boolean isPracticeMode(HttpServletRequest request) {
		String checkBox = request.getParameter("practice_mode");

		if(checkBox != null){
			this.practice_mode = true;
		}
		
		return this.practice_mode;
	}
	
	/**
	 * Sets the number of milliseconds this user took to complete this quiz
	 * @param time
	 */
	public void setStartTime(){
		this.startTime = System.currentTimeMillis();
	}
	
	/**
	 * Returns the number of seconds this quiz has been running
	 * @return
	 */
	public long getTime(){
		if(this.endTime == 0)
			this.endTime = System.currentTimeMillis();
		
		long time = (this.endTime - this.startTime)/1000;
		return time;
	}
	
	
	public String getTimeToString(){
		long time = this.getTime();
		int minutes = (int) time / 60;
		long seconds = time;
		if(minutes > 0)
			seconds = time % minutes;
		
		return minutes + " minutes and " + seconds + " seconds";
	}
	
	public static int getUsersTopScoreInQuiz(int quizID, String username) {
		String sql = String.format("SELECT MAX(score) as score FROM scores WHERE username = '%s' AND quizID = '%d';", username, quizID);
		
		DBConnection.getInstance().executeQuery(sql);
		ResultSet results = DBConnection.getInstance().executeQuery(sql);	

		try {
			if (results.next()) {
				return results.getInt("score");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
