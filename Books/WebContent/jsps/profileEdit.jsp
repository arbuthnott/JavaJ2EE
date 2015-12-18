<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Profile</title>
<%@ include file="/jsps/components/headerStuff.jsp" %>
<style>form label {width:200px; text-align:right;}</style>
</head>
<body>
	<%@ include file="/jsps/components/userNav.jsp" %>
	<h1>Edit Profile</h1>
	<hr />
	<p><a href="/books/profile">Back to Profile</a></p>
	<form class="form-inline" method="post" action="/books/profile">
		<label>Name:</label>
		<input class="form-control" type="text" name="name" value="${user.getName()}" required /><br />
		<label>Email:</label>
		<input class="form-control" type="email" name="email" value="${user.getEmail()}" required /><br />
		<button class="btn btn-primary" type="submit" name="updateProfile" value="${user.getId()}">Update Profile</button>
	</form>
	<img style="width:220px; height:220px; float:left; margin-right:20px;" src="${user.getImagePath()}" />
	<form method="post" action="/books/image" enctype="multipart/form-data">
		Upload Profile Image:
		<input class="btn" type="file" name="image" />
		<button class="btn btn-primary" type="submit" name="updateImage" value="${user.getId()}">Update Image</button>
	</form>
	
</body>
</html>