<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<%
	Quiz quiz = (Quiz) session.getAttribute("curr_quiz");
	boolean practice_mode = quiz.isPracticeMode(request);
	String timeStr = quiz.getTimeToString();

%>

<% if(practice_mode){ %>
	<h2>Practice Mode:</h2>
<% } %>

<br>You recieved <b><%= quiz.getPoints() %></b> out of <b><%= quiz.getTotalPoints() %></b> total points 
<br><br><em> It took you <%=timeStr %> to complete this quiz </em>

<% if(!practice_mode){ %>
	<br><b> Your score has been recorded.</b>
<% } else { %>
	<br><b>That was just practice, so no score was recorded.</b>

<% } %>

<% 
	//Remove the current quiz attribute
	session.setAttribute("curr_quiz", null);
%>

<%@ include file="helpers/end_boilerplate.jsp" %>