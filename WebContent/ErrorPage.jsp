<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name = "cssInclude" value = "css/create-quiz.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<h2>Whoops!</h2>
<br><br>
<%
String error_message = request.getParameter("error_message");
%>
<%=error_message %>
<br><br>
<em>Go back and try again.</em>
<%@ include file="helpers/end_boilerplate.jsp" %>