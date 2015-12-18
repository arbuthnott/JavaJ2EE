<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New Book</title>
<%@ include file="/jsps/components/headerStuff.jsp" %>
<style>form label {width:200px; text-align:right;}</style>
</head>
<body>
	<%@ include file="/jsps/components/userNav.jsp" %>
	<h1>New Inventory Item</h1>
	<hr />
	<p><a href="/books/list">Back to Book List</a></p>
	<h4>Enter Book Details</h4>
	
	
	<form method="post" action="/books/admin/new" class="form-inline">
		<label>Title:</label>
		<input class="form-control" type="text" name="title" required /><br />
		<label>Author:</label>
		<input class="form-control" type="text" name="author" required /><br />
		<label>Price: $</label>
		<input class="form-control" type="text" name="price" value="15.00" required pattern="[0-9]+\.[0-9][0-9]" /><br />
		<label>Available in store archive:</label>
		<input class="form-control" type="number" name="localStock" value="1" /><br />
		<label>Available at remote storage:</label>
		<input class="form-control" type="number" name="remoteStock" value="1" /><br />
		<button type="submit" class="btn btn-primary" name="newBook" value="1" >Create Book</button>
	</form>
	
</body>
</html>