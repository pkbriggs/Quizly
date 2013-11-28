<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name="cssInclude" value="css/index-page.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

   
<% if (!User.isLoggedIn(session)) { %>
	<!-- http://bootsnipp.com/snippets/featured/aboutme-login-style -->	
	<div class="index-content">
		<h1 class="index-header">Sign Up For Quizly:</h1>
			
		<!-- Log in using facebook or google -->
		<div class="row">
		  	<div class="col-xs-6 col-sm-6 col-md-6">
		  		<a href="#" class="btn btn-lg btn-facebook btn-block"><i class="fa fa-facebook fa-lg social-register-button-icon"></i> Facebook</a>
			</div>
			<div class="col-xs-6 col-sm-6 col-md-6">
				<a href="#" class="btn btn-lg btn-google btn-block"><i class="fa fa-google-plus fa-lg social-register-button-icon"></i> Google</a>
			</div>
		</div>
		
		<!-- "or" dividing line -->
		<div class="index-login-or">
			<hr class="index-hr-or">
			<span class="index-span-or">or</span>
		</div>
			
		<!-- Manual form with username and password -->	
		<form class="form" role="form" method="post" action="Register" accept-charset="UTF-8">
			<!-- Username -->
			<div class="form-group">
				<label for="username">Username</label>
			    <input type="text" class="form-control" name="register-username">
			</div>
			
			<!-- Password -->
			<div class="form-group">
				<label for="password">Password</label>
			    <input type="password" class="form-control" name="register-password">
			</div>
			
			<!-- Submit button -->
			<button type="submit" id="index-signup-button" class="btn btn-lg btn-primary btn-block">
				Sign up &raquo;
			</button>
		</form>
	</div>
		
	<% } else { %>
		<% String username = (String) session.getAttribute("username"); %>
		<h1>Welcome, <%= username %></h1>
		<a class="btn btn-lg btn-primary" href="/Quizly/quizzes.jsp" role="button">Take a quiz &raquo;</a>
	<% } %>


<%@ include file="helpers/end_boilerplate.jsp" %>
