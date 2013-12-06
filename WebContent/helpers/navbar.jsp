<%@ page import="users.User" %>
<%@ page import="users.Friendship" %>
<%@ page import="java.util.*" %>
<%@ page import="quizzes.*" %>
<%@ page import="dbconnection.DBConnection" %>


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
          <form role="form">
          <ul class="nav navbar-nav">
            <li class="active"><a href="/Quizly/">Home</a></li>
            <li><a href="/Quizly/quizzes.jsp">Quizzes</a></li>
            <li><a href="/Quizly/userlist.jsp">Users</a></li>
            <!-- <li class="dropdown">
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
            </li> -->
            
            <!-- Search -->
            <li class="dropdown" id="nav-search">
            	<div class="form-group">
			    	<input type="text" class="dropdown-toggle form-control input-xlarge" id="searchInput" placeholder="Search..." data-toggle="dropdown">
					<ul class="dropdown-menu" id="search-results">
					</ul>
				</div>
            </li>
            
          </ul>
          </form>
          <!-- <div class="nav navbar-nav" id="nav-search">
            
				<form role="form">
					<div class="form-group">
				    	<input type="text" class="form-control input-xlarge" id="searchInput" placeholder="Search...">
				  	</div>
				</form>
			
            
            
          	</div> -->
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
            		<% if (User.isLoggedIn(session)) { //  User.getIDFromUsername(User.getUsername(session)) %>
	            		<a href="#" class="user-action-icon dropdown-toggle" data-toggle="dropdown" data-currid="<%= User.getID(session) %>" id="user-dropdown"><i class="fa fa-users fa-2x"></i></a>
	            	<% } else { %>
	            		<a href="#" class="user-action-icon dropdown-toggle" data-toggle="dropdown" data-currid="-1" id="user-dropdown"><i class="fa fa-users fa-2x"></i></a>
	            	<% } %>
	            	<ul class="dropdown-menu" style="padding: 15px">
	            	
	            		<% if (User.isLoggedIn(session)) { %>
		            		<% List<Friendship> friendRequests = User.getFriendRequests(User.getUsername(session)); %>
		            		<% for (Friendship req: friendRequests) { %>
		            			<li>
		            				<div class="row nomarginrow">
			            				<a href="<%= "/Quizly/profile.jsp?id="+ req.getInitiatingUser() %>">
			            					<%= User.getUsernameFromID(req.getInitiatingUser()) %>
			            				</a>
			            				<a href="<%= "/Quizly/profile.jsp?id="+ req.getInitiatingUser() %>" class="btn btn-sm btn-primary">Respond</a>
		            				</div>
		            			</li>
		            		<% } %>
	            		<% } %>
	                </ul>
	            </li>
	            
	            <li>
	            	<a href="/Quizly/messages.jsp" class="user-action-icon"><i class="fa fa-comments fa-2x"></i></a>
	            </li>
	            
	            <li>
	            	<a href="/Quizly/challenges.jsp" class="user-action-icon"><i class="fa fa-bullhorn fa-2x"></i></a>
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