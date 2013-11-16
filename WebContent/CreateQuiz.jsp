<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
</head>
<script src="jquery.js"></script>
<%@ page import="java.util.*, quizzes.Quiz" %>
<%!

private void createQuiz(){
	
	
};
%>
<body>
<h1>Create Quiz</h1>
<form id= "initialize_quiz" action="CreateQuiz" method="post">
<input type = "hidden" name="formID" value ="initialize_quiz"/>
<input type="submit" value="Initialize Quiz"/>
</form>

<!-- This form submits the basic quiz info to the database and redirects to the success page-->
<form id= "quiz_info" action="CreateQuiz" method="post">
<input type = "hidden" name="formID" value ="submit_quiz"/>
Title: <textarea rows="1" cols="20" name="title"></textarea>
<p>Description: <textarea rows="5" cols="50" name="description"></textarea>
<input type="submit" value="Submit Quiz"/>
</form>

<!-- The form for creating a multiple choice question -->
<form id ="multiple_choice" action ="CreateQuiz" method="post" class="new_question">
<h2>New Multiple Choice Question</h2>
<p><em>Type in the question, the choices, and then select the correct answer.</em><p>
Question: <input type="text" name="question" size="40"/><p>
Choices: <p><input type="radio" name="radio" value="1"><input type="text" name="choice1"/></input><p>
<input type="radio" name="radio" value="2"><input type="text" name="choice2"/></input><p>
<input type="radio" name="radio" value="3"><input type="text" name="choice3"/></input><p>
<input type="radio" name="radio" value="4"><input type="text" name="choice4"/></input><p>
<input type = "hidden" name="formID" value = "multiple_choice"/>
<input type="submit" value="Add Question"/>
</form>


<!-- The form for creating a fill in the blank question -->
<form id ="fill_in_the_blank" action ="CreateQuiz" method="post" class="new_question">
<h2>New Fill-In-The-Blank Question</h2>
<p><em>Type in the question with a series of underscores like this: "______" to indicate where the participant should fill in their answer</em><p>
Question: <input type="text" name="question" size="50"/><p>
Answer: <input type="text" name="response" size="50"/><p>
<input type = "hidden" name="formID" value = "fill_in_the_blank"/>
<input type="submit" value="Add Question"/>
</form>

<!-- The form for creating a question-response -->
<form id ="question_response" action ="CreateQuiz" method="post" class="new_question">
<h2>New Question-Response</h2>
<p><em>Type in the question and then the correct response</em><p>
Question: <input type="text" name="question" size="50"/><p>
Response: <input type="text" name="response" size="50"/><p>
<input type = "hidden" name="formID" value = "question_response"/>
<input type="submit" value="Add Question"/>
</form>

<!-- The form for creating a  picture-response -->
<form id ="picture_response" action ="CreateQuiz" method="post" class="new_question">
<h2>New Picture-Response</h2>
<p><em>Type in the URL of the photo and then the correct response</em><p>
Photo URL: <input type="text" name="photo" size="50"/><p>
Response: <input type="text" name="response" size="50"/><p>
<input type = "hidden" name="formID" value = "picture_response"/>
<input type="submit" value="Add Question"/>
</form>
</body>
</html>