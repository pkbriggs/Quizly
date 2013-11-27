<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="users.User" %>
<%@ page import="quizzes.Quiz" %>
<%@ page import="users.Friendship" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<!-- <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css"> -->
<link rel="stylesheet" href="css/style.css">


<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Quizly | Quizzes</title>
</head>
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
            <li class="active"><a href="/Quizly/quizzes.jsp">Quizzes</a></li>
            <li><a href="/Quizly/userlist.jsp">Users</a></li>
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


    <div class="container">    
		<% if (!User.isLoggedIn(session)) { %>
			Please log in first.
		<% } else { %>
			<h1>List of available quizzes:</h1>			
			
			<p>You can also <a href="/Quizly/CreateQuiz.jsp">create a quiz</a>.</p>
			<% String quizzes = Quiz.listQuizzes(); %>
			<%=quizzes %>
		<% } %>
    </div> <!-- /container -->






	<!-- JQuery and Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
	
</body>
</html>