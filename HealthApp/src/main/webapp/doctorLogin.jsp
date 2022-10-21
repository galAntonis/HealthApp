<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<title>Insert title here</title>
</head>
<body>
	<h2>Signup Details</h2> 
	<form action="DoctorServlet" method="post"> 
		<input type="hidden" name="doctorlogin" value="docLogin"/>
		<table>
			<tr>
				<td>
					Doctor Amka:
				</td>
				<td>
					<input type="text" name="doctorAmka">
				</td> 
			</tr>
			<tr>
				<td>
					Password:
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
	<br><br>
	<a href="index.jsp"> &#60;&#60; Go back </a> 
</body>
</html>