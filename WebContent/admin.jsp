<%@ page import="users.User" %>
<%@ page import="users.Friendship" %>
<%@ page import="users.Friendship.FriendshipStatus" %>
<%@ page import="java.util.List" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly | Admin"/>
  <jsp:param name="cssInclude" value="css/admin.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<h2>Admin</h2>

<div id='admin_users'>
	Admin Users: 
	<% List<User> admin_users = User.getAdminUsers(); %>
	<ul>
	<% for (User user: admin_users) { %>
		<li>
			<a href='profile.jsp?id=<%= user.getID()%>' > <%= user.getUsername() %></a> 
			<a class='action' href='Admin?admin-demote=yes&userID=<%= user.getID()%>' >demote</a>			
		</li>
	<% } %>
	</ul>
</div>

<div id= 'reg_users'>
	Users:
	<% List<User> users = User.getAllUsers(); %>
	<ul>
	<% for (User user: users) { %>
		<li>
			<a href='profile.jsp?id=<%= user.getID()%>' > <%= user.getUsername() %></a> 
			<a class='action' href='Admin?admin-promote=yes&userID=<%= user.getID()%>' >promote</a>
			<a class='action' href='Admin?admin-delete=yes&userID=<%= user.getID()%>' >delete</a>	
		</li>
	<% } %>
	</ul>
</div>

<div id= 'quizzes'>
	
	Quizzes: 
	<% List<Quiz> quizzes = Quiz.GetArrayOfQuizzes("SELECT * FROM quizzes", "quizzes"); %>
	<ul>
	<% for (Quiz quiz: quizzes) { %>
		<li>
			<a href='QuizSummary.jsp?id=<%= quiz.getID()%>' > <%= quiz.getTitle() %></a>
			<a class='action' href='Admin?admin-quiz-delete=yes&quizID=<%= quiz.getID()%>' >delete</a>	
		
		</li>
	<% } %>
	</ul>
</div>
<%@ include file="helpers/end_boilerplate.jsp" %>