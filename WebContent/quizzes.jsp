<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>


<% if (!User.isLoggedIn(session)) { %>
	Please log in first.
<% } else { %>
	<h1>List of available quizzes:</h1>			
	
	<p>You can also <a href="/Quizly/CreateQuiz?formID=initialize_quiz">create a quiz</a>.</p>
	
	<% String quizzes = Quiz.listQuizzes("SELECT * FROM quizzes", "quizzes"); %>
	<%= quizzes %>
<% } %>


<%@ include file="helpers/end_boilerplate.jsp" %>