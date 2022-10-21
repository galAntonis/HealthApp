<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset='utf-8'>
	<meta name='viewport' content='width=divice-width, initial-scale=1'>
	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>
<title>Insert title here</title>
</head>
<body>
	<br/><br/>
	<h2> 
	<% 
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
	
		if(session.getAttribute("username")==null || session.getAttribute("password")==null)
		{
			response.sendRedirect("index.jsp");
		}
	
	%>
	 
	Profile of  <%=session.getAttribute("username") %>
	</h2> 
	<table border="1" class="table table-bordered table-responsive " style="background-color:white;">
		<tr>
			<th>AMKA</th>
			<th>User ID</th>
			<th>Όνομα</th>
			<th>Επώνυμο</th>
		</tr>
		<tr>
			<td><%=session.getAttribute("amka") %></td>
			<td><%=session.getAttribute("userid") %></td>
			<td><%=session.getAttribute("name") %></td>
			<td><%=session.getAttribute("surname") %></td>
		</tr>
	</table>
	<br><br>
	<a href="home.jsp"> &#60;&#60; Go back </a> 
</body>
</html>