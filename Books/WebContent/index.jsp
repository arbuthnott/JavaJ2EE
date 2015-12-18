<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Arbuthnott's Books</title>
</head>
<body>
	<h1>Index Page</h1>
	<c:forEach var="idx" begin="5" end="10" step="1">
		<c:choose>
			<c:when test="${idx % 2 == 1 }">
				<p style="background-color:lightgreen;">${idx}</p>
			</c:when>
			<c:otherwise>
				<p>${idx}</p>
			</c:otherwise>
		</c:choose>
	</c:forEach>
</body>
</html>