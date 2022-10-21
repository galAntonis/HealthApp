<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Sign up</title>
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
	Παρακαλώ εισάγετε τα στοιχεία χρήστη: <br>
	<form action="AdminServlet" method="post">
		<table>
			<tr>
				<td>
					<input type="hidden" name="admin" value="docregistration"/>
					Αριθμός Αδείας:
				</td>
				<td> 
					<input type="text" name="doctorLicenseNumber">
				</td>
			</tr>
			<tr>
				<td>
					Όνομα: 
				</td>
				<td>
					<input type="text" name="name">
				</td>
			</tr>
			<tr>
				<td>
					Επίθετο: 
				</td>
				<td>
					<input type="text" name="surname">
				</td>
			</tr>
			<tr>
				<td>
					Κωδικός: 
				</td>
				<td>
					<input type="password" name="password">
				</td>
			</tr>
			<tr>
				<td>
					Ηλικία: 
				</td>
				<td>
					<input type="number" name="age">
				</td>
			</tr>
			<tr>
				<td>
					Ειδικότητα: 
				</td>
				<td>
					<input type=radio id="pathologist" name="speciality" value="pathologist">
					<label for="pathologist">Παθολόγος</label>
					<input type=radio id="ophthalmologist" name="speciality" value="ophthalmologist">
					<label for="ophthalmologist">Οφθαλμίατρος</label>
					<input type=radio id="orthopedic" name="speciality" value="orthopedic">
					<label for="orthopedic">Ορθοπεδικός</label>
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
	<a href="adminHome.jsp"> &#60;&#60; Go back </a>
</body>
</html>