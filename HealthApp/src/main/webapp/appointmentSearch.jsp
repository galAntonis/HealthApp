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
	<title>Appointment Search</title>
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
	</h2>
	<br>
	<form action="login" method="post">
		Επιλέξτε ειδικότητα: <br>
		<input type="hidden" name="formlogin" value="specialitysearch"/>
		<input type=radio id="pathologist" name="speciality" value="pathologist">
		<label for="pathologist">Παθολόγος</label>
		<input type=radio id="ophthalmologist" name="speciality" value="ophthalmologist">
		<label for="ophthalmologist">Οφθαλμίατρος</label>
		<input type=radio id="orthopedic" name="speciality" value="orthopedic">
		<label for="orthopedic">Ορθοπεδικός</label>
		<input type="submit" value="Search">
	</form>
	<br><br><br>
	<form action="login" method="post">
		<input type="hidden" name="formlogin" value="datecheck"/>
		<table border=1 class="table table-bordered table-responsive " style="background-color:white;">
			<thead>
				<tr>
					<th>ΑΜΚΑ Ιατρού</th>
					<th>Όνομα</th>
		            <th>Επίθετο</th>
		            <th>Ειδικότητα</th>
		            <th>Έναρξη ραντεβού</th>
		            <th>Λήξη ραντεβού</th>
		            <th>Επιλογή</th>
		        </tr>
		    </thead>
		    <tbody>
		    	<c:forEach items="${requestScope.FreeDates}" var="s">
		    		<tr>
		    			<td><c:out value="${s.doctorAmka}"/></td>
		    			<td><c:out value="${s.doctorName}"/></td>
		    			<td><c:out value="${s.doctorSurname}"/></td>
		    			<td><c:out value="${s.doctorSpeciality}"/></td>
		    			<td><c:out value="${s.start_date}"/></td>
		    			<td><c:out value="${s.end_date}"/></td>
		    			<td><input type="radio" name="date" value="${s.doctorAmka},${s.start_date}"></td>
		    		</tr>
		    	</c:forEach>
			 </tbody>
	    </table>
	    <input type="submit" value="Κράτηση Ραντεβού">
    </form>
	<br><br>
	<a href="home.jsp"> &#60;&#60; Go Home </a>
</body>
</html>