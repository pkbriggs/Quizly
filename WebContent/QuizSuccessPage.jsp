<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name = "cssInclude" value = "css/QuizSuccess.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<% String achievement = (String) request.getAttribute("achievement"); %>

<% if(achievement != null) { %>
	<h2> Yahoo!!</h2> 
	
	<h3>You received a new Achievement : <b> <%=request.getAttribute("achievement") %> </b> </h3>
	<br><br>
<% } %>

<%
	Quiz quiz = (Quiz) session.getAttribute("curr_quiz");
	boolean practice_mode = quiz.isPracticeMode(request);
	String timeStr = quiz.getTimeToString();
	int score = (quiz.getPoints() * 100 ) / quiz.getTotalPoints();
%>

<% if(practice_mode){ %>
	<h1>Practice Mode</h1><br>
<% } %>

<h2>Score: <%=score %> % </h2>
<br>You recieved <b><%= quiz.getPoints() %></b> out of <b><%= quiz.getTotalPoints() %></b> total points 
<br><em> It took you <%=timeStr %> to complete this quiz </em>

<% if(!practice_mode){ %>
	<br><b> Your score has been recorded.</b>
<% } else { %>
	<br><b>That was just practice, so no score was recorded.</b>
<% } %>

<br><br>

<table>
	<tr>
		<th>Question</th>
		<th>Correct Response(s)</th>
		<th>Your Response(s)</th>
		<th>Your Score</th>
	</tr>
	<% String results = getResults(request); %>
	<%= results %>
</table>

<%!

String getResults(HttpServletRequest request){
	HttpSession session = request.getSession();
	Quiz quiz = (Quiz) session.getAttribute("curr_quiz");
	ArrayList<Question> questions = quiz.getAllQuestions();	
	
	String results = "";
	for(Question question : questions){
		results += GetResultRow(question);
	}
	return results;
}

private String GetResultRow(Question question){
	String html = "<tr>";
	html += "<td>";
	
	if(question.getType() == Question.PICTURE_RESPONSE){
		html += "<img src='"+question.getQuestion()+"' class='image'/>";
	}else
		html += question.getQuestion();
	
	html += "<td>";
	html += question.getCorrectResponses();
	
	html += "<td>";
	html += question.getUserResponses();
	
	html += "<td>";
	html += question.getScore();
	
	html+="</tr>";
	return html;
}

%>


<% 
	//Remove the current quiz attribute
	session.setAttribute("curr_quiz", null);
%>

<%@ include file="helpers/end_boilerplate.jsp" %>