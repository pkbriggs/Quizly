<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name="cssInclude" value="css/homepage.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<div id = 'btns'>
	<a class="btn btn-small btn-primary" href="/Quizly/quizzes.jsp" role="button">Take a quiz &raquo;</a>
	<a class="btn btn-small btn-primary" href="/Quizly/CreateQuiz?formID=initialize_quiz" role="button">Make a new quiz &raquo;</a>
</div>	

<div class='homepage'>
	<% String username = (String) session.getAttribute("username"); %>
	<h1>Welcome, <%= username %></h1>
		
	<div class='notification-section'>
		<h4>New Quizzes</h4>
		<div id='recently_created'>
			<% ArrayList<Quiz> recently_created = Quiz.GetArrayOfQuizzes("SELECT * FROM quizzes ORDER BY dateCreated LIMIT 5"); %>
		
			<table>
				<tr>
					<th>Quiz</th>
					<th>Created</th>
					<th>By</th>	
				</tr>
			
			<% for (Quiz quiz: recently_created) { %>
				<tr>
					<td><a href='QuizSummary.jsp?id=<%= quiz.getID()%>' > <%= quiz.getTitle() %></a> </td>
					<td><em><%=quiz.getDateCreated() %></em></td>
					<td><em><a href='profile.jsp?id=<%=User.getIDFromUsername(quiz.getCreator()) %>' > <%= quiz.getCreator() %></a></em></td>
				</tr>
			<% } %>
		</table>
		</div>
		
		<h4>Most Popular Now</h4>
		<div id='most_popular'>
			<% ArrayList<Score> most_popular = Score.getScores("SELECT *, COUNT(*) FROM scores GROUP BY quizID ORDER BY COUNT(*) LIMIT 10"); %>
			<table>
				<tr>
					<th>Quiz</th>
				</tr>
			
			<% for (Score score: most_popular) { %>
				<tr>
					<td><a href='QuizSummary.jsp?id=<%= score.getQuizID()%>' > <%= Quiz.getQuizTitleFromID(score.getQuizID()) %></a> </td>
				</tr>
			<% } %>
		</table>
		</div>
	</div>
</div>

<%@ include file="helpers/end_boilerplate.jsp" %>
