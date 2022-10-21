<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<title>Insert title here</title>
</head>
<body>
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
		 
		Welcome <%=session.getAttribute("username") %>
	</h2> 
	<br/>
	<form action="login" method="post"> 
		<input type="hidden" name="formlogin" value="profile"/>
		<input type="submit" name="profile" value="Το προφίλ μου">
	</form>
	<form action="login" method="post"> 
		<input type="hidden" name="formlogin" value="history"/>
		<input type="submit" name="profile" value="Ιστορικό Ραντεβού">
	</form>
	<form action="login" method="post"> 
		<input type="hidden" name="formlogin" value="comingdates"/>
		<input type="submit" name="profile" value="Προγραμματισμένα Ραντεβού">
	</form>
	<a href="appointmentSearch.jsp"><button>Αναζήτηση Ραντεβού</button></a><br>
	<form action="login" method="post">
		<input type="hidden" name="formlogin" value="canceldate"/>
		<input type="submit" name="profile" value="Ακύρωση Ραντεβού">
	</form>  
	<a href="logout.jsp">Logout</a> 
</body>
</html>