<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name="cssInclude" value="css/index-page.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<% String username = (String) session.getAttribute("username"); %>
<h1>Welcome, <%= username %></h1>
<a class="btn btn-lg btn-primary" href="/Quizly/quizzes.jsp" role="button">Take a quiz &raquo;</a>
<a class="btn btn-lg btn-primary" href="/Quizly/CreateQuiz?formID=initialize_quiz" role="button">Make a new quiz &raquo;</a>
		
<%@ include file="helpers/end_boilerplate.jsp" %>
