<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset='utf-8'>
<title>Insert title here</title>
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
		<a href="adminPatReg.jsp"><button>Εγγραφή νέου Χρήστη</button></a>
		<br>
		<a href="adminDocReg.jsp"><button>Εγγραφή νέου Ιατρού</button></a>
		<br>
		<a href="adminDocDel.jsp"><button>Διαγραφή Ιατρού</button></a>
		<form action="AdminServlet" method="post">
			<input type="hidden" name="admin" value="allpatients"/>
			<input type="submit" name="allpatients" value="Προβολή Ασθενών">
		</form>
		<form action="AdminServlet" method="post">
			<input type="hidden" name="admin" value="alldoctors"/>
			<input type="submit" name="alldoctors" value="Προβολή Ιατρών">
		</form>
		<br><a href="logout.jsp">Logout</a> 
</body>
</html>