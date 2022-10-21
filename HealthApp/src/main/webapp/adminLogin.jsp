<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<title>Είσοδος Admin</title>
</head>
<body>
	<form action="AdminServlet" method="post"> 
		<table>
			<tr>
				<td>
		 			<input type="hidden" name="admin" value="Login"/>
		 			Username: 
		 		</td>
		 		<td>
		 			<input type="text" name="adminUsername">
		 		</td>
		 	</tr>
		 	<tr>
		 		<td>
		 			Password:
		 		</td>
		 		<td>
		 			<input type="password" name="adminPassword">
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
</body>
</html>