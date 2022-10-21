<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Sign up</title>
</head>
<body>
	Παρακαλώ εισάγετε τα στοιχεία σας: <br>
	<form action="login" method="post">
		<table>
			<tr>
				<td>
					<input type="hidden" name="formlogin" value="register"/>
					AMKA:
				</td>
				<td> 
					<input type="text" name="amka">
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
				<td></td>
				<td>
					<input type="submit" value="Submit">
				</td>
			</tr>
			
		</table>
	</form>
	<br><br>
	<a href="login.jsp"> &#60;&#60; Go back </a>
</body>
</html>