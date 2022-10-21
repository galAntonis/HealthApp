<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name='viewport' content='width=divice-width, initial-scale=1'>
	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>
	<title>Appointment History</title>
</head>
<body>
	<% 
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
	
		if(session.getAttribute("name")==null)
		{
			response.sendRedirect("index.jsp");
		}
	
	%>
	<form action="DoctorServlet" method="post"> 
		<input type="hidden" name="doctorlogin" value="setdate"/>
		<table>
			<tr>
				<td>
					
					Έναρξη ραντεβού:
				</td>
				<td>
					<input type="text" name="start_date">
				</td>
			</tr>
			<tr>
				<td>
					Λήξη ραντεβού:
				</td>
				<td>
					<input type="text" name="end_date">
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	<br><br>
	<a href="doctorHome.jsp"> &#60;&#60; Go back </a> 
</body>
</html>
