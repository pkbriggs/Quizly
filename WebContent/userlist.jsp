<%@ page import="users.User" %>
<%@ page import="users.Friendship" %>
<%@ page import="java.util.List" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>


<% if (!User.isLoggedIn(session)) { %>
	Please log in first.
<% } else { %>
	<h1>List of all users:</h1>			
	
	<% List<User> users = User.getAllUsers(); %>
<% } %>


<%@ include file="helpers/end_boilerplate.jsp" %>