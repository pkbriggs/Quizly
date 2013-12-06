<%@ page import="messages.Message" %>

<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly | Messages"/>
  <jsp:param name="cssInclude" value="css/messages.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<div>
	<ul>
		<% Set<String> conversations = Message.getConversationList(User.getUsername(session)); %>
		<% for (String user: conversations) { %>
			<li class="conversation-selector">
				<%= user %>
			</li>
		<% } %>
	</ul>
</div>

<div>
	<ul>
		
	</ul>
</div>

<%@ include file="helpers/end_boilerplate.jsp" %>