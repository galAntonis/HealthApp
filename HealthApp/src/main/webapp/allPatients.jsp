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
	<title>All patients</title>
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
		<table border=1 class="table table-bordered table-responsive " style="background-color:white;">
		<thead>
			<tr>
				<th>AΜΚΑ</th>
	            <th>Όνομα</th>
	            <th>Επίθετο</th>
	            <th>Ηλικία</th>
	        </tr>
	    </thead>
	    <tbody>
	    	<c:forEach items="${requestScope.allPatients}" var="s">
	    		<tr>
	    			<td><c:out value="${s.amka}"/></td>
	    			<td><c:out value="${s.name}"/></td>
	    			<td><c:out value="${s.surname}"/></td>
	    			<td><c:out value="${s.age}"/></td>
	    		</tr>
	    	</c:forEach>
		 </tbody>
    </table>
    <br><br>
	<a href="adminHome.jsp"> &#60;&#60; Go back </a> 

</body>
</html>