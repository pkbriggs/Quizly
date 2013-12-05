<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="dbconnection.DBConnection, java.util.*, java.sql.*, quizzes.Quiz, java.util.Date" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<% 
	
		/*
		 * Gets the correct quiz and its values
		 */
		ServletContext sContext = getServletContext();
		DBConnection db = DBConnection.getInstance();
		//String quizID = request.getParameter("id");
		String quizID = "1";
		int quizIDInt = Integer.parseInt(quizID);
		Quiz quiz = new Quiz(quizIDInt);
		//String user = (String)session.getAttribute("username");
		String user = "stevenqian";
		String name = quiz.getTitle();
		String creator = quiz.getCreator();
		String dateCreated = quiz.getDateCreated();
		String description = quiz.getDescription();

		
		/*
		 * Class designed to keep track of an attempt
		 */
		class Attempt {
			String user;
			String score;
			String time;
			String date;
			Attempt() {
				//nothing
			}
			public void setUser(String user) {
				this.user = user;
			}
			public void setScore(String score) {
				this.score = score;
			}
			public void setTime(String time) {
				this.time = time;
			}
			public void setDate(String date) {
				this.date = date;
			}
			public double getScore() {
				return Double.parseDouble(score); 
			}
		}
		
		List<Attempt> attempts = new Vector<Attempt>(); //vector of all attempts for this quiz
		ResultSet rs2 = db.executeQuery("SELECT * FROM scores");
		while (rs2.next()) {
			if (rs2.getString("quizID").equals(quizID)) {
				Attempt curr = new Attempt();
				curr.setUser(rs2.getString("username"));
				curr.setScore(rs2.getString("score"));
				curr.setTime(rs2.getString("time"));
				curr.setDate(rs2.getString("dateTaken"));
				attempts.add(curr);
			}
		}
		
		
	%>
	<h1>Description</h1>
	<% 
		out.println("<p>" + quiz.getDescription() + "</p>");
	%>
	<h1>Creator</h1>
	<% 
		out.println("<p>" + creator + "</p>");
	%>
	<h1>Your Past Performances</h1>
	<% 
		List<Attempt> yourAttempts = new Vector<Attempt>();
		for (Attempt attempt: attempts) {
			System.out.println(user);
			if (attempt.user.equals(user)) {
				yourAttempts.add(attempt);
				System.out.println("added");
			}
		}
		for (Attempt attempt: yourAttempts) {
			out.println("<p>score: " + attempt.getScore() + "</p>");
		}
	%>
	<h1>Highest of All Time</h1>
	<% 
		class ScoreCompare implements Comparator<Attempt> {
			@Override
			public int compare(Attempt a1, Attempt a2) {
				double order = a2.getScore() - a1.getScore();
				if (order < 0) return -1;
				else if (order > 0) return 1;
				else return 0;
			}
		}
		Collections.sort(attempts, new ScoreCompare());
		int limit = 5;
		if (attempts.size() < 5) limit = attempts.size();
		for (int i = 0; i < limit; i++) {
			Attempt attempt = attempts.get(i);
			out.println("<p>score: " + attempt.getScore() + "</p>");
		}
	%>
	<h1>Highest of the Day</h1>
	<% 
		Date date = new Date();
		String today = date.toString().substring(20);//cuts off irrelevant information
		
	%>
	<h1>Recent Performances</h1>
	<% %>
	<h1>Total Performance Summary</h1>
	<% %>
	<h1>Take Quiz</h1>
	<% %>
	<h1>Practice Quiz</h1>
	<% %>
	<h1>Edit Quiz</h1>
	<% %>
</body>
</html>