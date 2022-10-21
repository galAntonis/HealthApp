<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<title>Login</title>
</head>
<body>
	<h2>Είσοδος Χρήστη</h2> 
	<form action="login" method="post"> 
	<table>
		<tr>
			<td>
				<input type="hidden" name="formlogin" value="Login"/>
				AMKA:
			</td>
			<td>
				<input type="text" name="amka">
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
			<td></td>
			<td>
				<input type="submit" value="Submit">
			</td>
		</tr> 
	</table>
	</form>
	<br><br>
	Δεν έχεις λογαρισμό; <a href="registration.jsp">Εγγραφή</a>
	<br><br>
	<a href="index.jsp"> &#60;&#60; Go back </a>  
</body>
</html>