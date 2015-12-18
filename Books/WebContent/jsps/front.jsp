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
	<h1>Welcome to Arbuthnott's Books</h1>
	<p>
		<blockquote>
			Welcome to the site.  Feel free to browse our selection of books available in store
			and at our remote storage location.  All items can be ordered in store, but logging
			in with your account will enable you to place orders from anywhere using this website.
		</blockquote><br />-Chris Arbuthnott, founder
	</p>
	<h3><a href="/books/login">Log in</a></h3>
	<h3><a href="/books/list">Browse Book List</a></h3>
	<h3><a href="/books/admin">Employee site</a></h3>
</body>
</html>