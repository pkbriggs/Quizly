<jsp:include page="/helpers/boilerplate.jsp">
  <jsp:param name="pageTitle" value="Quizly"/>
  <jsp:param name="cssInclude" value="css/homepage.css" />
</jsp:include>
<%@ include file="helpers/navbar.jsp" %>

<h2> Yahoo!!</h2> 

<h3>You recieved a new Achievement : <b> <%=request.getAttribute("achievment") %> </b> </h3>

<%@ include file="helpers/end_boilerplate.jsp" %>
