<%@ page import="messages.Challenge" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name="cssInclude" value="css/challenges.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<div class="challenges-page">
	<div class="col-md-6 col-md-offset-3 conversations">
		<h3>
			Challenges 
			<a href="#" class="pull-right new-challenge" data-toggle="modal" data-target="#challengeModal">new challenge <i class="fa fa-plus"></i></a>
		</h3>
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
						<%= challenge.username %> challenged you to 
						<a href='/Quizly/QuizSummary.jsp?id=<%= challenge.quizId %>'><%= challenge.quizName %></a>. 
						Their high score: <%= Quiz.getUsersTopScoreInQuiz(challenge.quizId, challenge.username) %>
					</p>
					<p>
						<button class="btn btn-sm btn-success accept-challenge-button" data-quizid='<%= challenge.quizId %>' data-challengeid='<%= challenge.challengeId %>'>Accept</button>
					    <button class="btn btn-sm btn-primary decline-challenge-button" data-quizid='<%= challenge.quizId %>' data-challengeid='<%= challenge.challengeId %>'>Decline</button>
					</p>
				</li>
			<% } %>
			<% if (challenges.size() == 0) { %>
				<li>No new challenges.</li>
			<% } %>
		</ul>
	</div>
	
	<!-- new challenge modal -->
	<div class="modal fade" id="challengeModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title">New challenge</h4>
	      </div>
	      <div class="modal-body">
	        
	        <p>Please search for a friend to challenge.</p>
	        
	        <div class="form-group">
		    	<input type="text" class="dropdown-toggle form-control input-xlarge" id="challenge-search" placeholder="Search..." data-toggle="dropdown">
				<ul class="dropdown-menu" id="challenge-search-results">
				</ul>
			</div>
			
			<div id="challenged-user">
			</div>
			
			<p id="challenge-quiz-select-instructions">Now select a quiz:</p>
			<% List<Quiz> quizList = Quiz.GetArrayOfQuizzes("SELECT * FROM quizzes;", "quizzes"); %>
			<select class="form-control" id="challenge-quiz-select">
				<% for (Quiz quiz: quizList) { %>
					<option data-quizid="<%= quiz.getID() %>"><%= quiz.getTitle() %></option>
				<% } %>
			</select>
        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" id="send-challenge" disabled="disabled">Send challenge</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
	
</div>

<script src="js/challenges.js"></script>
<%@ include file="helpers/end_boilerplate.jsp" %>