
<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name = "cssInclude" value = "css/create-quiz.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>


<h1>Create Quiz</h1><br><br>



<!-- This form submits the basic quiz info to the database and redirects to the success page-->
<form id= "quiz_info" action="CreateQuiz" method="post">
<input type = "hidden" name="formID" value ="submit_quiz"/>
<h3>Title</h3> <textarea rows="1" cols="20" name="title"></textarea><br>
<h3>Description</h3> <textarea rows="5" cols="50" name="description"></textarea>
<p>Display on Multiple Pages <input type='checkbox' name='multiple_pages' id='multiple_pages' value='multiple_pages'/>
<div id='multiple_pages_section'>
<label id='pages_label'>Number of questions per page:</label>
<input type='text' id='questions_per_page' name='questions_per_page'/>
</div>
<h3 id = "questionNum"># of Questions: </h3> <label id='num_questions_so_far'>0</label><br><br>
<input class = "btn btn-question" type="submit" value="+ Submit Quiz"/><br><br>
</form>

<!-- The form for creating a multiple choice question -->
<button id = "multipleChoiceButton"class="btn btn-small btn-expand">+</button>
<h2>New Multiple Choice Question</h2>

<form id ="multiple_choice" action ="CreateQuiz" method="post" class="new_question">
<p><em>Type in the question, the choices, and then select the correct answer.</em><p>
Question: <input type="text" name="question" size="40"/><p>
Choices: <p><input type="radio" name="radio" value="1"><input class="shiftRight" type="text" name="choice1"/></input><p>
<input type="radio" name="radio" value="2"><input class="shiftRight" type="text" name="choice2"/></input><p>
<input type="radio" name="radio" value="3"><input class="shiftRight" type="text" name="choice3"/></input><p>
<input type="radio" name="radio" value="4"><input class="shiftRight" type="text" name="choice4"/></input><p>
<input type = "hidden" name="formID" value = "multiple_choice"/>
<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form><br><br>


<!-- The form for creating a fill in the blank question -->
<button id = "fillQuestionButton"class="btn btn-small btn-expand">+</button>
<h2>New Fill-In-The-Blank Question</h2>
<form id ="fill_in_the_blank" action ="CreateQuiz" method="post" class="new_question">
<p><em>Type in the question with a series of underscores like this: "______" to indicate where the participant should fill in their answer</em><p>
Question: <input type="text" name="question" size="50"/><p>
<div id='fib_answers'>
<input type='hidden' id='fib_num_answers' name='fib_num_answers' value='1'/>
Correct Answer(s): <input type="text" name="answer0" size="50"/><p>
</div>
<input type = "hidden" name="formID" value = "fill_in_the_blank"/>
<input class="btn btn-question" type="button" id='add_fib_answer' value="+ Add Another Answer"/><br><br>
<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form><br><br>

<!-- The form for creating a question-response -->
<button id = "questionResponseButton"class="btn btn-small btn-expand">+</button>
<h2>New Question-Response</h2>
<form id ="question_response" action ="CreateQuiz" method="post" class="new_question">
<p><em>Type in the question and then the correct response</em><p>
Question: <input type="text" name="question" size="50"/><p>
<div id='qr_answers'>
<input type='hidden' id='qr_num_answers' name='qr_num_answers' value='1'/>
Correct Answer(s): <input type="text" name="answer0" size="50"/><p>
</div>
<input type = "hidden" name="formID" value = "question_response"/>
<input class="btn btn-question" type="button" id='add_qr_answer' value="+ Add Another Answer"/><br><br>
<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form><br><br>

<!-- The form for creating a  picture-response -->
<button id = "pictureResponseButton"class="btn btn-small btn-expand">+</button>
<h2>New Picture-Response</h2>
<form id ="picture_response" action ="CreateQuiz" method="post" class="new_question">
<p><em>Type in the URL of the photo and then the correct response</em><p>
Photo URL: <input type="text" name="photo" size="50"/><p>
<div id='pr_answers'>
<input type='hidden' id='pr_num_answers' name='pr_num_answers' value='1'/>
Correct Answer(s): <input type="text" name="answer0" size="50"/><p>
</div>
<input type = "hidden" name="formID" value = "picture_response"/>
<input class="btn btn-question" type="button" id='add_pr_answer' value="+ Add Another Answer"/><br><br>
<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form>
<script src='jquery.js'></script>
<script src='CreateQuizHelpers.js'></script>

<%@ include file="helpers/end_boilerplate.jsp" %>