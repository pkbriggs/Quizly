<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name = "cssInclude" value = "css/create-quiz.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<script src='js/CreateQuiz.js'></script>

<h1>Create A Quiz</h1><br><br>

<button class = "btn btn-small btn-expand" id='get_quiz_info' >Enter Quiz Info and Submit</button>

<!--form submits the quiz to the database-->
<form id= "quiz_info" action="CreateQuiz" method="post">
	
	<!--Submits Quiz -->
	<input class = "btn btn-question" type="submit" value="+ Submit"/>
	<br><br>
	
	<input type = "hidden" name="formID" value ="submit_quiz"/>
		
	<!-- Title of the Quiz -->
	<h3>Title</h3> 
	<textarea rows="1" cols="20" name="title"></textarea><br>
	
	<!-- Description of the Quiz -->
	<h3>Description</h3> 
	<textarea rows="5" cols="50" name="description"></textarea>
	
	<!-- Option to randomize Quiz questions-->
	<br>Randomize Questions:
	<input type='checkbox' name='randomize' value='randomize'/>
	
	<!-- Option to display Quiz on multiple pages-->
	<br>Display on Multiple Pages:
	<input type='checkbox' name='multiple_pages' id='multiple_pages' value='multiple_pages'/>
	<div id='multiple_pages_section'>
		<label id='pages_label' class="shiftRight" >Number of questions per page:</label>
		<input class="shiftRight" type='text' id='questions_per_page' name='questions_per_page' size='5'/>
	</div>

</form>

<% 
	int num_questions = 0;
	session = request.getSession();
	Quiz currQuiz = (Quiz) session.getAttribute("quiz_being_created");
	if(currQuiz != null)
		num_questions = currQuiz.getAllQuestions().size();
	else
		System.out.println("curreQuiz still null");
%>
<br><br>

<!-- To Create a multiple choice question -->
<div id='mc_title'>
	<h4 id = "questionNum">Number of Questions So Far: </h4> <label id='num_questions_so_far'><%=num_questions %></label>
	<br><br>
	<button id = "multipleChoiceButton" class="btn btn-small btn-expand">+</button>
	<h2>New Multiple Choice Question</h2>
</div>
<form id ="multiple_choice" action ="CreateQuiz" method="post" class="new_question">
	<br><em>Type in the question, the choices, and then check the correct answer(s).</em><br>
	Question: <input type="text" name="question" size="40"/>
	<br><br><b>Choices: </b>
	<br>
	<b>Multiple Answers: </b>(<em>eg. 'Select all the following that are true'</em>)  <input type="checkbox" name='mc_multiple_responses' id='mc_multiple_responses'/>
	<br><br>
	<!-- Checkbox buttons for choices -->
	<input type="radio" class ="mc_answer" name="answer" value="choice0">
		<input class="shiftRight" type="text" name="choice0"/></input><br>
	<input type="radio" class ="mc_answer" name="answer" value="choice1">
		<input class="shiftRight" type="text" name="choice1"/></input><br>
	<input type="radio" class ="mc_answer" name="answer" value="choice2">
		<input class="shiftRight" type="text" name="choice2"/></input><br>
	<input type="radio"  class ="mc_answer" name="answer" value="choice3">
		<input class="shiftRight" type="text" name="choice3"/></input><br>
	
	<input type = "hidden" name="formID" value = "multiple_choice"/>
	<input type = "hidden" name="create_quiz" value = "create_quiz"/>
	
	<br>
	<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form>
<br>
<br>


<!-- The form for creating a fill in the blank question -->
<div id='fib_title'>
	<button id = "fillQuestionButton"class="btn btn-small btn-expand">+</button>
	<h2>New Fill-In-The-Blank Question</h2>
</div>

<form id ="fill_in_the_blank" action ="CreateQuiz" method="post" class="new_question">
	<br><br><b>If there are more than one possible correct answers to a question (for example, "MemChu" or "Memorial Church" might both be correct) separate them with a "|"</b>
	<br><br><em>To indicate where the participant should fill in the blank, place series of underscores like this: "______" where the blank should be</em>
	<br><br>
	Question: <input type="text" name="question" size="50"/>
	<br><br>
	
	Correct Answer(s): 
	<input type="text" name="answer" size="50"/>
	<br><br>
	
	<input type = "hidden" name="create_quiz" value = "create_quiz"/>
	<input type = "hidden" name="formID" value = "fill_in_the_blank"/>
	<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form>
<br>
<br>

<!-- The form for creating a question-response -->
<div id='qr_title'>
	<button id = "questionResponseButton"class="btn btn-small btn-expand">+</button>
	<h2>New Question-Response</h2>
</div>

<form id ="question_response" action ="CreateQuiz" method="post" class="new_question">
	<br>
	<b>Use a "|" to separate between correct possible versions of the same response. </b>
	<br><em>Example 1 (Single Response): </em> "What is the church on Stanford's campus?" "Memorial Church | memchu"
	<br><br><em>Example 2 (Multiple Response, unordered): </em> "Name five US states." In this case, enter all 50 states as possible correct responses
	<br><br><em> Example 3 (Multiple Response, ordered): </em> "Name the 10 most populous countries." Enter all 10 countries as correct responses in the order they should appear in the quiz-taker's response.
	<br><br>Multiple Responses (eg. 'Name 5 US states'): <input type = "checkbox" id="multiple_responses" name="multiple_responses"/>
	<br>
	Question: <input type="text" name="question" size="50"/>
	<br><br>
	<div id='multiple_responses_section'>
		<b>Number of responses : </b><input type="text" name="num_responses" size=5" /> <em>(This is the number of responses the quiz-taker should give. So in the case of 'Name 5 US states' you would put 5 here, and then enter all 50 states as answers below.)</em>
		<br><br><b>Ordered: </b> <input type = "checkbox" name="ordered" value="ordered"/> <em>(check this box if the responses should be given in a certain order, eg. 'Name the 5 most populous countries')</em>
	</div>
	
	<div id='qr_answers'>
		<input type='hidden' id='qr_num_answers' name='qr_num_answers' value='1'/>
		
		<br><br><b>Correct Answer(s): </b>
		<br>
		<input type="text" name="answer" size="50"/>
		<br><br>
	</div>
	
	<input type = "hidden" name="create_quiz" value = "create_quiz"/>
	<input type = "hidden" name="formID" value = "question_response"/>
	<input class="btn btn-question" type="button" id='add_qr_answer' value="+ Add Another Answer"/><br><br>
	<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form>
<br>
<br>

<!-- The form for creating a  picture-response -->
<div id='pr_title'>
	<button id = "pictureResponseButton"class="btn btn-small btn-expand">+</button>
	<h2>New Picture-Response</h2>
</div>

<form id ="picture_response" action ="CreateQuiz" method="post" class="new_question">
	<br>
	<br><b>Use a "|" to separate between correct possible versions of the same response. </b>

	<br><br><em>Type in the URL of the photo and then the correct response</em>
	<br><br>
	Photo URL: <input type="text" name="photo" size="50"/>
	<br><br>
	
	<div id='pr_answers'>
		<input type='hidden' id='pr_num_answers' name='pr_num_answers' value='1'/>
		Correct Answer(s): <input type="text" name="answer" size="50"/>
		<br><br>
	</div>
	
	<input type = "hidden" name="create_quiz" value = "create_quiz"/>
	<input type = "hidden" name="formID" value = "picture_response"/>
	<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form>

<%@ include file="helpers/end_boilerplate.jsp" %>