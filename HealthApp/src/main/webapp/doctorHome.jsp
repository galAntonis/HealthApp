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
		
			if(session.getAttribute("name")==null)
			{
				response.sendRedirect("login.jsp");
			}
		
		%>
		 
		Welcome <%=session.getAttribute("name") %>
	</h2>
	<br/>
	<a href="doctorSetDates.jsp"><button>Καταχώρηση Διαθεσιμότητας</button></a><br>
	<form action="DoctorServlet" method="post"> 
		<input type="hidden" name="doctorlogin" value="dates"/>
		<input type="submit" name="dates" value="Τα ραντεβού μου">
	</form>
	<form action="DoctorServlet" method="post"> 
		<input type="hidden" name="doctorlogin" value="canceldate"/>
		<input type="submit" name="dates" value="Ακύρωση Ραντεβού">
	</form>
	<a href="logout.jsp">Logout</a> 
</body>
</html>