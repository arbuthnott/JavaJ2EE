<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Inventory</title>
<%@ include file="/jsps/components/headerStuff.jsp" %>
<style>form label {width:200px; text-align:right;}</style>
</head>
<body>
	<%@ include file="/jsps/components/userNav.jsp" %>
	<h1>Edit Book Details</h1>
	<hr />
	<p><a href="/books/list">Back to Book List</a></p>
	<h2>
		<strong>${ book.getTitle() }</strong>
	</h2>
	
	<h4>Edit Details: Book id: ${book.getId() }</h4>
	<form method="post" action="/books/admin" class="form-inline">
		<label>Title:</label>
		<input class="form-control" type="text" name="title" value="${book.getTitle()}" required /><br />
		<label>Author:</label>
		<input class="form-control" type="text" name="author" value="${book.getAuthor()}" required /><br />
		<label>Price: $</label>
		<input class="form-control" type="text" name="price" value="${book.getPrice()}" required pattern="[0-9]+\.[0-9][0-9]" /><br />
		<label>Available in store archive:</label>
		<input class="form-control" type="number" name="localStock" value="${book.getLocalStock()}" /><br />
		<label>Available at remote storage:</label>
		<input class="form-control" type="number" name="remoteStock" value="${book.getRemoteStock()}" /><br />
		<button type="submit" class="btn btn-primary" name="updateBook" value="${book.getId()}">Apply Changes</button>
	</form>
	
</body>
</html>