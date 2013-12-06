<%@ page import="messages.Message" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly | Messages"/>
  <jsp:param name="cssInclude" value="css/messages.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<div class="messages-page">
<div class="col-md-4 col-md-offset-1 conversations">
	<h3>Conversations</h3>
	<ul class="conversation-list">
		<% List<User> friends = User.getFriends(User.getID(session)); %>
		<% for (User user: friends) { %>
			<li class="conversation" data-userid="<%= user.getID() %>"><a href="#">
				<% if (user.getPhotoFilename() != null) { %> <!-- The user has a profile picture, use it! -->
					<img src="<%= user.getPhotoFilename() %>" class="small-profile-picture" />
				<% } else { %> <!-- The user doesn't have a profile picture, use the default -->
					<img src="http://blogs.utexas.edu/bonnecazegroup/files/2013/02/blank-profile-hi.png" class="small-profile-picture" />
				<% } %>
				<%= user.getUsername() %>
				<i class="fa fa-arrow-right"></i>
			</a></li>
		<% } %>
	</ul>
</div>

<div class="col-md-6">
	<h3>Messages &nbsp;<a href="#"><i class="fa fa-refresh" id="refresh-messages"></i></a></h3>
	<ul class="message-list">
		<li>No conversation selected.</li>
	</ul>
	
	<div class="message-input-container">
		<input type="text" placeholder="Write a reply..." class="form-control" id="message-input">
	</div>
</div>
</div>

<script src="js/messages.js"></script>
<%@ include file="helpers/end_boilerplate.jsp" %>