<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Logout</title>
</head>
<body>
	<% 
		session.removeAttribute("username"); 
		session.removeAttribute("password"); 
		session.removeAttribute("amka");
		session.removeAttribute("userid");
		session.removeAttribute("name");
		session.removeAttribute("surname");
		session.removeAttribute("start_date");
		session.removeAttribute("end_date");
		session.removeAttribute("adminid");
		session.removeAttribute("adminUsername");
		session.removeAttribute("adminPassword");
		session.invalidate(); 
	%> 
	<h1>Logout was done successfully.</h1>
	<a href="home.jsp"> &#60;&#60; Go Home </a>  
</body>
</html>