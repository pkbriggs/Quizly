<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<!-- <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css"> -->
<link rel="stylesheet" href="css/index-page.css">

<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Quizly | Home</title>
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
            <li class="active"><a href="/Quizly/">Home</a></li>
            <li><a href="#">Quizzes</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <b class="caret"></b></a>
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
            
            
            <% if (session.getAttribute("loggedin") == null || (Boolean)session.getAttribute("loggedin") == false) { %>
            
            	<!-- <li><a href="#">Register</a></li> -->
	            <li class="dropdown">
	               <a href="#" class="dropdown-toggle" data-toggle="dropdown">Log in <b class="caret"></b></a>
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
	                                 <button type="submit" class="btn btn-success btn-block">Sign in</button>
	                              </div>
	                           </form>
	                        </div>
	                     </div>
	                  </li>
	                  <li class="divider"></li>
	                  <li>
	                     <input class="btn btn-primary btn-block" type="button" id="sign-in-google" value="Sign In with Facebook">
	                     <input class="btn btn-primary btn-block" type="button" id="sign-in-twitter" value="Sign In with Google">
	                  </li>
	               </ul>
	            </li>
                  
            <% } else { %>
            
           		<li><a href="#"><%= session.getAttribute("username") %></a></li>
            	<li><a href="Logout">Log out</a></li>
            
            <% } %>
            
            
            
            
            
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </div>


    <div class="container">    
		<% if (session.getAttribute("loggedin") == null || (Boolean)session.getAttribute("loggedin") == false) { %>
			<!-- http://bootsnipp.com/snippets/featured/aboutme-login-style -->
		
			<div class="index-content">
			<h1 class="index-header">Sign Up For Quizly:</h1>
			
			<div class="row">
			  <div class="col-xs-6 col-sm-6 col-md-6">
			    <a href="#" class="btn btn-lg btn-primary btn-block">Facebook</a>
			  </div>
			  <div class="col-xs-6 col-sm-6 col-md-6">
			    <a href="#" class="btn btn-lg btn-info btn-block">Google</a>
			  </div>
			</div>
			<div class="index-login-or">
			  <hr class="index-hr-or">
			  <span class="index-span-or">or</span>
			</div>
			
			<form class="form" role="form" method="post" action="Register" accept-charset="UTF-8">
			  <div class="form-group">
			    <label for="username">Username</label>
			    <input type="text" class="form-control" name="register-username">
			  </div>
			  <div class="form-group">
			    <label for="password">Password</label>
			    <input type="password" class="form-control" name="register-password">
			  </div>
			  <button type="submit" id="index-signup-button" class="btn btn-lg btn-primary btn-block">
			    Sign Up
			  </button>
			</form>
		</div>
			
		<% } else { %>
			<% String username = (String) session.getAttribute("username"); %>
			<h1>Welcome, <%= username %></h1>
			<a class="btn btn-lg btn-primary" href="#" role="button">Take a quiz &raquo;</a>
		<% } %>
		
    </div> <!-- /container -->






	<!-- JQuery and Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
	
</body>
</html>