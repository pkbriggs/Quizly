<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="dbconnection.DBConnection, java.util.*, java.sql.*, quizzes.Quiz" %>
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
		DBConnection db = (DBConnection) sContext.getAttribute("dbconnection");
		String quizID = request.getParameter("id");
		int quizIDInt = Integer.parseInt(quizID);
		Quiz quiz = Quiz.getQuiz(quizIDInt);
		String name = quiz.getTitle();
		String creator = quiz.getCreator();
		String dateCreated = quiz.getDateCreated();
		String description = quiz.getDescription();

		//Checks if quiz is never set
		if (quiz == null) System.out.println("Invalid ID");
		
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
		}
		
		Vector<Attempt> attempts = new Vector<Attempt>(); //vector of all attempts for this quiz
		ResultSet rs2 = db.executeQuery("SELECT * FROM quizhistory");
		while (rs2.next()) {
			if (rs2.getString("quizID").equals(quizID)) {
				Attempt curr = new Attempt();
				curr.setUser(rs2.getString("userID"));
				curr.setScore(rs2.getString("accuracy"));
				curr.setTime(rs2.getString("time"));
				curr.setDate(rs2.getString("date"));
				attempts.add(curr);
			}
		}
		
		
	%>
	<h1>Description</h1>
	<% 
		System.out.println("<p>" + quiz.getDescription() + "</p>");
	%>
	<h1>Creator</h1>
	<% 
		System.out.println("<p>" + creator + "</p>");
	%>
	<h1>Past Performances</h1>
	<% %>
	<h1>Highest of All Time</h1>
	<% %>
	<h1>Highest of the Day</h1>
	<% %>
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