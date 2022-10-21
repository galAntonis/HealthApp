<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Delete Doctor</title>
</head>
<body>
		<% 
			response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
			response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
			response.setHeader("Expires", "0"); // Proxies.
		
			if(session.getAttribute("adminUsername")==null || session.getAttribute("adminPassword")==null)
			{
				response.sendRedirect("index.jsp");
			}
		
		%>
		Εισάγετε στοιχεία για διαγραφή ιατρού: <br>
		<form action="AdminServlet" method="post">
			<table>
				<tr>
					<td>
						<input type="hidden" name="admin" value="deletedoctor"/>
						Αριθμός Αδείας:
					</td>
					<td>
					 	<input type="text" name="doctorLicenseNumber">
					 </td>
				</tr>
				<tr>
					<td>
						Κωδικός Διαχειριστή:
					</td>
					<td>
						<input type="password" name="password">
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
		<br><Br>
		<a href="adminHome.jsp"> &#60;&#60; Go back </a>
</body>
</html>