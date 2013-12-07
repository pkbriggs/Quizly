<%@page import="dbconnection.DBConnection"%>
<%@ page import="java.util.List" %>
<%@ page import="quizzes.*" %>
<%@ page import="users.User" %>	
<%@ page import="java.util.concurrent.TimeUnit" %>
	
<a class="btn btn-small btn-primary" href="/Quizly/quizzes.jsp" role="button">Take a quiz &raquo;</a>
<a class="btn btn-small btn-primary" href="/Quizly/CreateQuiz?formID=initialize_quiz" role="button">Make a new quiz &raquo;</a>

<% String username = (String) session.getAttribute("username"); %>
<h1>Welcome, <%= username %>.</h1>
	
<div class='notification-section'>
	<div class="row">
		<div id='recently_created' class="col-md-6">
			<h3>New Quizzes</h3>
			<% List<Quiz> recently_created = Quiz.GetArrayOfQuizzes("SELECT * FROM quizzes ORDER BY dateCreated LIMIT 5"); %>
		
			<table class="table table-hover table-striped">
				<tr>
					<th>Quiz</th>
					<th>Created</th>
					<th>By</th>	
				</tr>
			
			<% for (Quiz quiz: recently_created) { %>
				<tr>
					<td><a href='QuizSummary.jsp?id=<%= quiz.getID()%>' > <%= quiz.getTitle() %></a> </td>
					<td><em><%=quiz.getDateCreated() %></em></td>
					<td><a href='profile.jsp?id=<%=User.getIDFromUsername(quiz.getCreator()) %>' > <%= quiz.getCreator() %></a></td>
				</tr>
			<% } %>
		</table>
		</div>
		
		<div id='most_popular' class="col-md-6">
			<h3>Most Popular Now</h3>
			<% List<Score> most_popular = Score.getScores("SELECT *, COUNT(*) FROM scores GROUP BY quizID ORDER BY COUNT(*) LIMIT 5"); %>
			<table class="table table-hover table-striped">
				<tr>
					<th>Quiz</th>
					<th>Description</th>
				</tr>
			
			<% for (Score score: most_popular) { %>
				<tr>
					<td><a href='QuizSummary.jsp?id=<%= score.getQuizID()%>'><%= Quiz.getQuizTitleFromID(score.getQuizID()) %></a></td>
					<td><%= Quiz.getQuiz(score.getQuizID()).getDescription() %></td>
				</tr>
			<% } %>
		</table>
		</div>
	</div>
	
	<div class="row">
		<div class="col-md-6">
			<h3>Your Recent Quizzes</h3>
			<table class="table table-hover table-striped">
				<tr>
					<th>Quiz</th>
					<th>Score</th>
					<th>Time</th>
				</tr>
				
				<% List<Score> recentQuizzes = Score.getScores("SELECT * FROM scores ORDER BY dateTaken ASC LIMIT 5"); %>
				<% for (Score score: recentQuizzes) { %>
					
					<tr>
						<td><a href='QuizSummary.jsp?id=<%= score.getQuizID() %>'><%= Quiz.getQuizTitleFromID(score.getQuizID()) %></a></td>
						<td><%= score.getScore() * 100 %>%</td>
						<td><%= String.format("%dm %ds", 
							    TimeUnit.SECONDS.toMinutes(score.getTime()),
							    TimeUnit.SECONDS.toSeconds(score.getTime()) - 
							    TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(score.getTime()))
							) %></td>
					</tr>
				<% } %>
				
			</table>
		</div>
		
		<div class="col-md-6">
		<h3>Quizzes You've Created</h3>
			<table class="table table-hover table-striped">
				<tr>
					<th>Quiz</th>
					<th>Description</th>
				</tr>
				
				<% List<Quiz> created = Quiz.GetArrayOfQuizzes(String.format("SELECT * FROM quizzes WHERE creator = '%s' ORDER BY dateCreated ASC LIMIT 5", User.getUsername(session))); %>
				<% for (Quiz quiz: created) { %>
					
					<tr>
						<td><a href='QuizSummary.jsp?id=<%= quiz.getID() %>'><%= quiz.getTitle() %></a></td>
						<td><%= quiz.getDescription() %></td>
					</tr>
				<% } %>
				
			</table>
		</div>
	</div>
</div>

