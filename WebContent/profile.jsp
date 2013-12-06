<%@ page import="users.User" %>
<%@ page import="users.Friendship" %>
<%@ page import="users.Friendship.FriendshipStatus" %>
<%@ page import="java.util.List" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly | Profile"/>
  <jsp:param name="cssInclude" value="css/profile.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>
   
<!-- Whether this is one's own profile. This is false when viewing another's profile -->
<% boolean viewing_self_profile = true; %>

<div class="profile-page">
<% if (!User.isLoggedIn(session)) { %>
	Please log in first.
<% } else { %>
	<% String idString = request.getParameter("id"); %>
	<% int userID = -1; %>
	<% if (idString != null) userID = Integer.parseInt(idString); System.out.println("userId:" + userID); %>
	
	<div class="row">
		<div class="profile-user-section">
			
			<% if (idString == null || userID == User.getID(session)) { %>
				<!-- this is the user's profile page -->
				<figure>
					<img src="http://blogs.utexas.edu/bonnecazegroup/files/2013/02/blank-profile-hi.png" class="profile-picture" data-userid="<%= User.getID(session) %>" />
					<figcaption><a href="#">Change profile picture...</a></figcaption>
				</figure>
				
				<h2><%= User.getUsername(session) %></h2>
				<span>(you)</span>
				
			<% } else { %>
				<!-- Viewing somebody else's profile -->
				<% viewing_self_profile = false ; %>
				<img src="http://blogs.utexas.edu/bonnecazegroup/files/2013/02/blank-profile-hi.png" class="profile-picture" data-userid="<%= userID %>" />
				
				<% if (userID == -1) { %>
					<h2>User not found</h2>
				<% } else { %>
					<h2><%= User.getUsernameFromID(userID) %></h2>
				
					<!-- Add/remove/accept/reject friend button stuff -->
					<% FriendshipStatus friendStatus = Friendship.getFriendship(User.getID(session), userID); %>
					<% System.out.println("Friendstatus = " + friendStatus); %>
					<% if (friendStatus == FriendshipStatus.FRIENDS) { %>
						<!-- A button with a dropdown to remove the friend -->
						<div class="btn-group">
						  <button type="button" id="friends-button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
						    Friends <i class="fa fa-check"></i> 
						  </button>
						  <ul class="dropdown-menu" role="menu">
						    <li><a href="#" id="remove-friend">Remove friend</a></li>
						  </ul>
						</div>
					<% } else if (friendStatus == FriendshipStatus.NOT_FRIENDS) { %>
						<button class="btn btn-primary" id="add-friend">Add friend</button>
					<% } else if (friendStatus == FriendshipStatus.REQUEST_SENT) { %>
						<button class="btn btn-success" disabled="disabled">Request sent <i class="fa fa-check"></i></button>
					<% } else if (friendStatus == FriendshipStatus.REQUEST_RECEIVED) { %>
						<p>This person sent you a friend request:</p>
						<p>
							<button class="btn btn-success" id="accept-request">Accept</button>
							<button class="btn btn-primary" id="reject-request">Reject</button>
						</p>
					<% } %>
				<% } %>
			<% } %>
		</div>
		
		<div class="profile-content-section">
			<% String user =  (viewing_self_profile) ? "You" : User.getUsernameFromID(userID); %>
			<% ArrayList<Quiz> quizzes_created = Quiz.GetArrayOfQuizzes("SELECT * FROM quizzes WHERE creator='"+User.getUsernameFromID(userID)+"'"); %>
			<% ArrayList<Score> quizzes_taken = Score.getScores("SELECT * FROM scores WHERE username='"+User.getUsernameFromID(userID)+"' LIMIT 8 ORDER BY dateTaken"); %>
			<% ArrayList<Quiz> recently_created = Quiz.GetArrayOfQuizzes("SELECT * FROM quizzes LIMIT 8 ORDER BY dateCreated"); %>
			
			<h3>New Quizzes</h3>
			<div id='recently_created'>
				<table>
				<% for (Quiz quiz: recently_created) { %>
					<tr>
						<td><a href='QuizSummary.jsp?id=<%= quiz.getID()%>' > <%= quiz.getTitle() %></a> </td>
						<td><em>Created: <%=quiz.getDateCreated() %></em></td>
						<td><em>By: <a href='profile.jsp?id=<%= User.getIDFromUsername(quiz.getCreator()) %>' > <%= quiz.getCreator() %></a></em></td>
					</tr>
				<% } %>
				</table>
			</div>
			
			
			<button id = "quizzes_created" class="btn btn-small btn-expand">+</button>
			<h3> Quizzes <%= user %> Created </h3>
			<div id='quizzes_created_div'>
				<table>
				<% for (Quiz quiz: quizzes_created) { %>
					<tr>
						<td><a href='QuizSummary.jsp?id=<%= quiz.getID()%>' > <%= quiz.getTitle() %></a> </td>
						<td><em>Created: <%=quiz.getDateCreated() %></em></td>
					</tr>
				<% } %>
				</table>
			</div>
			
			<% String user_phrase =  (viewing_self_profile) ? "You Have" : User.getUsernameFromID(userID) + " Has"; %>
			<button id = "quizzes_taken" class="btn btn-small btn-expand">+</button>	
			<h3> Quizzes <%= user_phrase %> Taken </h3>
			<div id='quizzes_taken_div'>
				<table>
				<% for (Score score: quizzes_taken) { %>
					
					<tr>
						<td><a href='QuizSummary.jsp?id=<%= score.getQuizID()%>' > <%= quiz.getTitle() %></a> </td>
						<td><em>Taken: <%=quiz.getDateCreated() %></em></td>
					</tr>
				<% } %>
				</table>
			</div>
				
		</div>
	</div> <!-- end row -->
<% } %>
</div>

<script src="js/profile.js"></script>
<%@ include file="helpers/end_boilerplate.jsp" %>