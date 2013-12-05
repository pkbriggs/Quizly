<%@ page import="messages.Challenge" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<% if (!User.isLoggedIn(session)) { %>
	Please log in first.
<% } else { %>
	<% List<Challenge> challenges = Challenge.getUserChallenges(User.getUsername(session), 0); %>
	<ul>
	<% for (Challenge challenge: challenges) { %>
		<li>
			<a href="/Quizly/profile.jsp?id=<%= User.getIDFromUsername(challenge.username) %>"><%= challenge.username %></a>
			 challenged you to <b><%= challenge.quizName %></b> (high score: ).
			<button class="btn btn-sm btn-success">Accept</button>
		    <button class="btn btn-sm btn-primary">Decline</button>
		</li>
	<% } %>
	</ul>
<% } %>

<%@ include file="helpers/end_boilerplate.jsp" %>