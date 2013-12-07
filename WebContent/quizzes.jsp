<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>


<% if (!User.isLoggedIn(session)) { %>
	Please log in first.
<% } else { %>
	<h1>List of available quizzes:</h1>			
	
	<p>You can also <a href="/Quizly/CreateQuiz?formID=initialize_quiz">create a quiz</a>.</p>
	
	<% ArrayList<Quiz> quizzes = Quiz.GetArrayOfQuizzes("SELECT * FROM quizzes"); %>
	<ul>
		<% for(Quiz quiz: quizzes){ %>
			<li><a href='QuizSummary.jsp?id=<%=quiz.getID()%>' > <%= quiz.getTitle()%> </a> </li>
		<% } %>
	</ul>;
<% } %>


<%@ include file="helpers/end_boilerplate.jsp" %>