
<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly | Admin"/>
  <jsp:param name="cssInclude" value="css/admin.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<h2>Administration</h2>

<div id='admin_users'>
	<h3>Admin Users: </h3>
	<% List<User> admin_users = User.getAdminUsers(); %>
	<table class='shiftRight'>
	<% for (User user: admin_users) { %>
		<tr>
			<td><a href='profile.jsp?id=<%= user.getID()%>' > <%= user.getUsername() %></a></td> 
			<td><a class='btn btn-small btn-primary' href='Admin?admin-demote=yes&userID=<%= user.getID()%>' >demote  &raquo;</a></td>			
		</tr>
	<% } %>
	</table>
</div>

<div id= 'reg_users'>
	<h3> Users:</h3>
	<% List<User> users = User.getAllUsers(); %>
	<table class='shiftRight'>
	<% for (User user: users) { %>
		<tr>
			<td><a href='profile.jsp?id=<%= user.getID()%>' > <%= user.getUsername() %></a> </td>
			<td><a class='btn btn-small btn-primary' href='Admin?admin-promote=yes&userID=<%= user.getID()%>' >promote</a></td>
			<td><a class='btn btn-small btn-primary' href='Admin?admin-delete=yes&userID=<%= user.getID()%>' >delete</a></td>
		</tr>
	<% } %>
	</table>
</div>

<div id= 'quizzes'>
	
	<h3> Quizzes: </h3>
	<% List<Quiz> quizzes = Quiz.GetArrayOfQuizzes("SELECT * FROM quizzes"); %>
	<table class='shiftRight'>
	<% for (Quiz quiz: quizzes) { %>
		<tr>
			<td><a href='QuizSummary.jsp?id=<%= quiz.getID()%>' > <%= quiz.getTitle() %></a></td>
			<td><a class='btn btn-small btn-primary' href='Admin?admin-quiz-delete=yes&quizID=<%= quiz.getID()%>' >delete</a></td>
		
		</tr>
	<% } %>
	</table>
</div>
<div id='announcements'>
	<h3>Announcements</h3>
	<form>
		<label>Subject</label>
		<textarea id="sub" rows="1" cols="20" name="subject"></textarea><br><br>
		<label>Body</label>
		<textarea id="bod" rows="5" cols="50" name="body"></textarea><br><br>
		<a class='btn btn-small btn-primary' href='Admin?admin-announce=yes&userID=<% User.getUsername(session); %>&subject='>Submit</a>
	</form>
</div>
<%@ include file="helpers/end_boilerplate.jsp" %>