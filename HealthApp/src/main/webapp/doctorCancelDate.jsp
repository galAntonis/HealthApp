<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name='viewport' content='width=divice-width, initial-scale=1'>
	<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css'>
	<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js'></script>
	<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js'></script>
	<title>Cancel Appointment</title>
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
	<form action="DoctorServlet" method="post" >
		<input type="hidden" name="doctorlogin" value="canceldate"/>
		<table border=1 class="table table-bordered table-responsive " style="background-color:white;">
			<thead>
				<tr>
					<th>ΑΜΚΑ Ασθενή</th>
					<th>Όνομα</th>
			        <th>Επίθετο</th>
			        <th>Ηλικία</th>
			        <th>Έναρξη ραντεβού</th>
			        <th>Λήξη ραντεβού</th>
					<th>Επιλογή</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${requestScope.legalCancellation}" var="s">
			    	<tr>
			    		<td><c:out value="${s.patientAmka}"/></td>
			    		<td><c:out value="${s.patientName}"/></td>
			    		<td><c:out value="${s.patientSurname}"/></td>
			    		<td><c:out value="${s.age}"/></td>
			    		<td><c:out value="${s.start_date}"/></td>
			    		<td><c:out value="${s.end_date}"/></td>
			    		<td><input type="radio" name="cancelation" value="${s.patientAmka},${s.start_date}"></td>
			    	</tr>
			    </c:forEach>
			 </tbody>
		</table>
		<input type="submit" value="Ακύρωση Ραντεβού">
	</form>
	<br>
	*Σημείωση: Οι ακυρώσεις γίνονται τουλάχιστον 3 μέρες νωρίτερα
	<br><br>
	<a href="doctorHome.jsp"> &#60;&#60; Go back </a> 
</body>
</html>