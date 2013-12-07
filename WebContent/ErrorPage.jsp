<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name = "cssInclude" value = "css/create-quiz.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<h2>Whoops!</h2>
<br><br>

<h3><%= request.getParameter("error_message") %></h3>
<br><br>
<em>Go back and try again.</em>
<%@ include file="helpers/end_boilerplate.jsp" %>