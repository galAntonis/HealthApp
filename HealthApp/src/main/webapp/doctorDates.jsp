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
	<title>My Appointments</title>
</head>
<body>
	<br/><br/>
	<% 
		
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setHeader("Expires", "0"); // Proxies.
	
		if(session.getAttribute("name")==null)
		{
			response.sendRedirect("index.jsp");
		}
	
	%>
	<table border=1 class="table table-bordered table-responsive " style="background-color:white;">
		<thead>
			<tr>
				<th>ΑΜΚΑ</th>
				<th>Όνομα</th>
	            <th>Επίθετο</th>
	            <th>Ηλικία</th>
	            <th>Έναρξη ραντεβού</th>
	            <th>Λήξη ραντεβού</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${requestScope.ComingAppointments}" var="s">
	    		<tr>
	    			<td><c:out value="${s.patientAmka}"/></td>
	    			<td><c:out value="${s.patientName}"/></td>
	    			<td><c:out value="${s.patientSurname}"/></td>
	    			<td><c:out value="${s.age}"/></td>
	    			<td><c:out value="${s.start_date}"/></td>
	    			<td><c:out value="${s.end_date}"/></td>
	    		</tr>
	    	</c:forEach>
		 </tbody>
    </table>
    <br><br>
    <a href="doctorHome.jsp"> &#60;&#60; Go back </a> 
</body>
</html>