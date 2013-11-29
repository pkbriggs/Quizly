<%@ page import="users.User" %>
<%@ page import="users.Friendship" %>
<%@ page import="java.util.List" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly | Profile"/>
  <jsp:param name="cssInclude" value="css/profile.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>
   
<div class="profile-page">
<% if (!User.isLoggedIn(session)) { %>
	Please log in first.
<% } else { %>
	<% String idString = request.getParameter("id"); %>
	<% int userID = -1; %>
	<% if (idString != null) userID = Integer.parseInt(idString); %>
	
	<% if (idString != null || userID == User.getID(session)) { %>
		<% User user = User.getUserFromID(userID); %>
		<% if (user == null) { // this ID does not exist in database %>
			<p>The specified user ID does not exist. Please try again.</p>
		<% } else { %>
			<div class="row">
				<div class="profile-user-section">
					<img src="http://blogs.utexas.edu/bonnecazegroup/files/2013/02/blank-profile-hi.png" class="profile-picture" data-userid="<%= user.getID() %>" />
					<h2><%= user.getUsername() %></h2>
					<button class="btn btn-primary friend-button">Add friend</button>
				</div>
				<div class="profile-content-section">
					<p>Content here</p>
				</div>
			</div>
		<% } %>
	<% } else { %>
		<div class="row">
				<div class="profile-user-section">
					<img src="http://blogs.utexas.edu/bonnecazegroup/files/2013/02/blank-profile-hi.png" class="profile-picture" />
					<h2><%= User.getUsername(session) %></h2> <span>(you)</span>
				</div>
				<div class="profile-content-section">
					<p>Content here</p>
				</div>
			</div>
	<% } %>
<% } %>
</div>

<script src="js/profile.js"></script>
<%@ include file="helpers/end_boilerplate.jsp" %>