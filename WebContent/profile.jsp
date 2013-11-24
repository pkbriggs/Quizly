<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@ page import="users.User" %>
<%@ page import="users.Friendship" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<!-- <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css"> -->
<link rel="stylesheet" href="css/index-page.css">

<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Quizly | Your Profile</title>
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
            
            <li class="dropdown">
            	<a href="#" class="dropdown-toggle" data-toggle="dropdown">FR <b class="caret"></b></a>
            	<ul class="dropdown-menu" style="padding: 15px">
	                  <li>
	                     <div class="row">
	                     </div>
                     </li>
                </ul>
            </li>
            
            <li class="dropdown">
            	<a href="#" class="dropdown-toggle" data-toggle="dropdown">NO <b class="caret"></b></a>
            	<ul class="dropdown-menu" style="padding: 15px;min-width: 250px;">
	                  <li>
	                     <div class="row">
	                     </div>
                     </li>
                </ul>
            </li>
            
            <li class="dropdown">
            	<a href="#" class="dropdown-toggle" data-toggle="dropdown">CH <b class="caret"></b></a>
            	<ul class="dropdown-menu" style="padding: 15px;min-width: 250px;">
	                  <li class="pull-right">
	                     <div class="row">
	                     </div>
                     </li>
                </ul>
            </li>
            
            
            <% if (!User.isLoggedIn(session)) { %>
            
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
            	
            	<li id="dd" class="wrapper-dropdown-5" tabindex="1">John Doe
				    <ul class="dropdown">
				        <li><a href="#"><i class="icon-user"></i>Profile</a></li>
				        <li><a href="#"><i class="icon-cog"></i>Settings</a></li>
				        <li><a href="#"><i class="icon-remove"></i>Log out</a></li>
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
			<h1>Welcome to your profile, <%= User.getUsername(session) %></h1>
			<!-- <a class="btn btn-lg btn-primary" href="#" role="button">Take a quiz &raquo;</a> -->
			
			<h3>Friend Requests</h3>
			<% List<Friendship> friendRequests = User.getFriendRequests(User.getUsername(session), getServletContext()); %>
			
			<% if (friendRequests.size() == 0) { %>
				<p>You have no new friend requests.</p>
			<% } else { %>
			
				<ul>
				<% for (Friendship friendship: friendRequests) { %>
					<li>
						<%= User.getUsernameFromID(friendship.getInitiatingUser(), getServletContext()) %>
						<button class="btn">Accept</button>
						<button class="btn">Reject</button>
					</li>
				<% } %>
				</ul>
				
			<% } %>
		<% } %>
    </div> <!-- /container -->






	<!-- JQuery and Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
	<script src="//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js"></script>
	
</body>
</html>