<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create or Take a Quiz</title>
</head>
<%!
private void listQuizzes(){
	
}
%>
<body>
<form id= "initialize_quiz" action="CreateQuiz" method="post">
<input type = "hidden" name="formID" value ="initialize_quiz"/>
<input type="submit" value="Create new Quiz"/>
</form>

<form action ="TakeQuiz" method="post">
Take Quiz: 
<% listQuizzes(); %>
<input type="submit" value="Take Quiz"/>
</form>

</body>
</html>