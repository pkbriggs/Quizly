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
	<h1>Welcome to your profile, <%= User.getUsername(session) %>!</h1>
	<!-- <a class="btn btn-lg btn-primary" href="#" role="button">Take a quiz &raquo;</a> -->
		
	<h3>Friend Requests</h3>
	<% List<Friendship> friendRequests = User.getFriendRequests(User.getUsername(session)); %>
			
	<% if (friendRequests.size() == 0) { %>
		<p>You have no new friend requests.</p>
	<% } else { %>
				
		<ul>
			<% for (Friendship friendship: friendRequests) { %>
				<li>
					<%= User.getUsernameFromID(friendship.getInitiatingUser()) %>
					<button class="btn">Accept</button>
					<button class="btn">Reject</button>
				</li>
			<% } %>
		</ul>
				
	<% } %>
<% } %>

<%@ include file="helpers/end_boilerplate.jsp" %>