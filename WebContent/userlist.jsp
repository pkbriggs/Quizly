<%@ page import="users.User" %>
<%@ page import="users.Friendship" %>
<%@ page import="java.util.List" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<h1>List of all users:</h1>			

<% List<User> users = User.getAllUsers(); %>
<ul>
<% for (User user: users) { %>
	<li><a href='profile.jsp?id=<%= user.getID()%>' > <%= user.getUsername() %></a></li>
<% } %>
</ul>


<%@ include file="helpers/end_boilerplate.jsp" %>