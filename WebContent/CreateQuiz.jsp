<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
</head>
<script src="jquery.js"></script>
<script src="CreateQuizHelper.js"></script>
<%@ page import="java.util.*, quizzes.Quiz" %>
<%!

private void createQuiz(){
	//Quiz new_quiz = new Quiz();
	
};
%>
<body>
<h1>Create Quiz</h1>
<!-- This form submits the basic quiz info to the database
AT THE MOMENT THIS FORM MUST BE SUBMITTED BEFORE STARTING TO CREATE QUESTIONS-->
<form id= "quiz_info" action="createQuiz()" >
Title: <textarea rows="1" cols="20"></textarea>
<p>Description: <textarea rows="5" cols="50"></textarea>
<input type="submit" value="Start Making Quiz"/>
</form>

<!-- The form for creating a multiple choice question -->
<form id ="multiple_choice" action ="CreateQuiz" method="post" class="new_question">

<input type="submit" value="Add Question"/>
</form>

</body>
</html>