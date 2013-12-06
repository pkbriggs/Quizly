<%@ page import="messages.Challenge" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name="cssInclude" value="css/challenges.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<div class="challenges-page">
	<div class="col-md-6 col-md-offset-3 conversations">
		<h3>Challenges</h3>
		<ul class="challenge-list">
			<% List<Challenge> challenges = Challenge.getUserChallenges(User.getUsername(session), 0); %>
			<% for (Challenge challenge: challenges) { %>
			<% User user = User.getUserFromUsername(challenge.username); %>
				<li>
					<% if (user.getPhotoFilename() != null) { %> <!-- The user has a profile picture, use it! -->
						<img src="<%= user.getPhotoFilename() %>" class="small-profile-picture" />
					<% } else { %> <!-- The user doesn't have a profile picture, use the default -->
						<img src="http://blogs.utexas.edu/bonnecazegroup/files/2013/02/blank-profile-hi.png" class="small-profile-picture" />
					<% } %>
					
					<p>
						<%= challenge.username %>
						challenged you to QUIZ. Their high score: <%= Quiz.getUsersTopScoreInQuiz(challenge.quizId, challenge.username) %>
					</p>
					<p>
						<button class="btn btn-sm btn-success">Accept</button>
					    <button class="btn btn-sm btn-primary">Decline</button>
					</p>
				</li>
			<% } %>
		</ul>
	</div>
</div>

<%@ include file="helpers/end_boilerplate.jsp" %>