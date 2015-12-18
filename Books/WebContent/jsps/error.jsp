<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Arbuthnott's Books</title>
<%@ include file="/jsps/components/headerStuff.jsp" %>
</head>
<body>
	<%@ include file="/jsps/components/userNav.jsp" %>
	<h1>Something went wrong</h1>
	<p style="color:red">${errorMessage}</p>
	<p>Try returning to the homepage, and attempting your action again.</p>
	<h3><a href="/books">Back to Home Page</a></h3>
</body>
</html>