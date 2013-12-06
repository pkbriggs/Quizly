<%@ page import="users.User" %>
<%@ page import="users.Friendship" %>
<%@ page import="users.Friendship.FriendshipStatus" %>
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
			<p>Content here</p>
		</div>
	</div> <!-- end row -->
<% } %>
</div>

<script src="js/profile.js"></script>
<%@ include file="helpers/end_boilerplate.jsp" %>