<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700' rel='stylesheet' type='text/css'>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css">
<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">
<!-- <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css"> -->
<link rel="stylesheet" href="css/style.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script src="js/global.js"></script>

<% String cssInclude = request.getParameter("cssInclude"); %>
<% if (cssInclude != null) { %>
	<link rel="stylesheet" href="<%= cssInclude %>">
<% } %>


<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title><%= request.getParameter("pageTitle") %></title>
</head>
<body>