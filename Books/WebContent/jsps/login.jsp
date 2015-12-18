<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	<h1>Login to Arbuthnott's Books</h1>
	<p style="color:red;">${loginMessage }</p>
	<form method="post" action="/books/login">
		<input type="hidden" name="destination" value="${destination}" />
		<div class="form-group">
			<label for="email">Email:</label>
			<input type="email" name="email" required class="form-control" />
		</div>
		<div class="form-group">
			<label for="password">Password:</label>
			<input type="password" name="password" required class="form-control" />
		</div>
			<input type="submit" name="login" value="Log In" class="btn btn-primary" />
	</form>
	
	
</body>
</html>