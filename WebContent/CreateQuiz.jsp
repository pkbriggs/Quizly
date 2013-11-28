<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Quiz</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/create-quiz.css" />

<script src='jquery.js'></script>
<script src='CreateQuizHelpers.js'></script>
</head>
<%@ page import="java.util.*, quizzes.Quiz" %>
<%@ page import="users.User" %>
<%@ page import="users.Friendship" %>
<%@ page import="java.util.List" %>
<body>

<!-- Static navbar -->
    <div class="navbar navbar-default navbar-static-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="/Quizly/">Quizly</a>
        </div>
        <div class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li><a href="/Quizly/">Home</a></li>
            <li><a href="/Quizly/quizzes.jsp">Quizzes</a></li>
            <li class="active"><a href="/Quizly/userlist.jsp">Users</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <i class="fa fa-sort-asc"></i></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">            
            
            <% if (!User.isLoggedIn(session)) { %>
            
            	<!-- <li><a href="#">Register</a></li> -->
	            <li class="dropdown">
	               <a href="#" class="dropdown-toggle" data-toggle="dropdown">Log in <i class="fa fa-sort-asc"></i></a>
	               <ul class="dropdown-menu" style="padding: 15px;min-width: 250px;">
	                  <li>
	                     <div class="row">
	                        <div class="col-md-12">
	                           <form class="form" role="form" method="post" action="Login" accept-charset="UTF-8" id="login-nav">
	                              <div class="form-group">
	                                 <label class="sr-only" for="username">Username</label>
	                                 <input type="text" class="form-control" id="username" placeholder="Username" name="login-username" required>
	                              </div>
	                              <div class="form-group">
	                                 <label class="sr-only" for="password">Password</label>
	                                 <input type="password" class="form-control" id="password" name="login-password" placeholder="Password" required>
	                              </div>
	                              <div class="checkbox">
	                                 <label>
	                                 <input type="checkbox"> Remember me
	                                 </label>
	                              </div>
	                              <div class="form-group">
	                                 <button type="submit" class="btn btn-success btn-block">Sign in &raquo;</button>
	                              </div>
	                           </form>
	                        </div>
	                     </div>
	                  </li>
	                  <li class="divider"></li>
	                  <li>
	                  	 <a href="#" class="btn btn-lg btn-facebook-small btn-block"><i class="fa fa-facebook social-register-button-icon"></i> Sign In with Facebook</a>
	                     <a href="#" class="btn btn-lg btn-google-small btn-block"><i class="fa fa-google-plus social-register-button-icon"></i> Sign In with Google</a>
	                  </li>
	               </ul>
	            </li>
                  
            <% } else { %>
            
            	<li class="dropdown">
	            	<a href="#" class="user-action-icon dropdown-toggle" data-toggle="dropdown"><i class="fa fa-users fa-2x"></i></a>
	            	<ul class="dropdown-menu" style="padding: 15px">
		                  <li>
		                     <div class="row">
		                     </div>
	                     </li>
	                </ul>
	            </li>
	            
	            <li class="dropdown">
	            	<a href="#" class="user-action-icon dropdown-toggle" data-toggle="dropdown"><i class="fa fa-comments fa-2x"></i></a>
	            	<ul class="dropdown-menu" style="padding: 15px;min-width: 250px;">
		                  <li>
		                     <div class="row">
		                     </div>
	                     </li>
	                </ul>
	            </li>
	            
	            <li class="dropdown">
	            	<a href="#" class="user-action-icon dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bullhorn fa-2x"></i></a>
	            	<ul class="dropdown-menu" style="padding: 15px;min-width: 250px;">
		                  <li class="pull-right">
		                     <div class="row">
		                     </div>
	                     </li>
	                </ul>
	            </li>
        
            	<li class="dropdown" id="user-dropdown">
            		<a href="#" class="dropdown-toggle" data-toggle="dropdown"><%= User.getUsername(session) %> <i class="fa fa-sort-asc"></i></a>
	            	<ul class="dropdown-menu">
	            		<li><a href="/Quizly/profile.jsp"><i class="fa fa-user fa-fw fa-lg"></i>Profile</a></li>
				        <li><a href="#"><i class="fa fa-cog fa-fw fa-lg"></i>Settings</a></li>
				        <li><a href="/Quizly/Logout"><i class="fa fa-sign-out fa-fw fa-lg"></i>Log out</a></li>
	                </ul>
            	</li>
            
            <% } %>
            
            
            
            
            
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>


<h1>Create Quiz</h1>



<!-- This form submits the basic quiz info to the database and redirects to the success page-->
<form id= "quiz_info" action="CreateQuiz" method="post">
<input type = "hidden" name="formID" value ="submit_quiz"/>
Title: <textarea rows="1" cols="20" name="title"></textarea><br><br>
<p>Description: <textarea rows="5" cols="50" name="description"></textarea>
<p>Display on Multiple Pages: <input type='checkbox' name='multiple_pages' id='multiple_pages' value='multiple_pages'/>
<div id='multiple_pages_section'>
<label id='pages_label'>Number of questions per page:</label>
<input type='text' id='questions_per_page' name='questions_per_page'/>
</div>
Number of questions so far: <label id='num_questions_so_far'>0</label><br>
<input class = "btn btn-question" type="submit" value="+ Submit Quiz"/>
</form>

<!-- The form for creating a multiple choice question -->
<form id ="multiple_choice" action ="CreateQuiz" method="post" class="new_question">
<h2>New Multiple Choice Question</h2>
<p><em>Type in the question, the choices, and then select the correct answer.</em><p>
Question: <input type="text" name="question" size="40"/><p>
Choices: <p><input type="radio" name="radio" value="1"><input class="shiftRight" type="text" name="choice1"/></input><p>
<input type="radio" name="radio" value="2"><input class="shiftRight" type="text" name="choice2"/></input><p>
<input type="radio" name="radio" value="3"><input class="shiftRight" type="text" name="choice3"/></input><p>
<input type="radio" name="radio" value="4"><input class="shiftRight" type="text" name="choice4"/></input><p>
<input type = "hidden" name="formID" value = "multiple_choice"/>
<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form>


<!-- The form for creating a fill in the blank question -->
<form id ="fill_in_the_blank" action ="CreateQuiz" method="post" class="new_question">
<h2>New Fill-In-The-Blank Question</h2>
<p><em>Type in the question with a series of underscores like this: "______" to indicate where the participant should fill in their answer</em><p>
Question: <input type="text" name="question" size="50"/><p>
<div id='fib_answers'>
<input type='hidden' id='fib_num_answers' name='fib_num_answers' value='1'/>
Correct Answer(s): <input type="text" name="answer0" size="50"/><p>
</div>
<input type = "hidden" name="formID" value = "fill_in_the_blank"/>
<input class="btn btn-question" type="button" id='add_fib_answer' value="+ Add Another Answer"/><br><br>
<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form>

<!-- The form for creating a question-response -->
<form id ="question_response" action ="CreateQuiz" method="post" class="new_question">
<h2>New Question-Response</h2>
<p><em>Type in the question and then the correct response</em><p>
Question: <input type="text" name="question" size="50"/><p>
<div id='qr_answers'>
<input type='hidden' id='qr_num_answers' name='qr_num_answers' value='1'/>
Correct Answer(s): <input type="text" name="answer0" size="50"/><p>
</div>
<input type = "hidden" name="formID" value = "question_response"/>
<input class="btn btn-question" type="button" id='add_qr_answer' value="+ Add Another Answer"/><br><br>
<input class="btn btn-question" type="submit" value="+ Add Question"/>
</form>

<!-- The form for creating a  picture-response -->
<form id ="picture_response" action ="CreateQuiz" method="post" class="new_question">
<h2>New Picture-Response</h2>
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

<!-- JQuery and Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
</body>
</html>