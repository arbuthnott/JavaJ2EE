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
	<img style="width:220px; height:220px; float:left; margin-right:20px;" src="${user.getImagePath()}" />
	<h1>Profile Page</h1>
	<hr />
	<p style="color:red">${profileMessage}</p>
	<ul>
		<li>Name: ${user.getName() }</li>
		<li>Email: ${user.getEmail() }</li>
		<li>
			<c:choose>
			<c:when test="${user.isAdmin() }">
				Administrator
			</c:when>
			<c:otherwise>
				Valued Customer
			</c:otherwise>
		</c:choose>
		</li>
	</ul>
	
	<form method="post" action="/books/profile">
		<button type="submit" class="btn btn-primary" name="editProfile" value="${user.getId()}">
			Edit Profile
		</button>
	</form>
	
	<c:if test="${orders.size() > 0}">
	<h3>Current Orders:</h3>
	<ul>
		<c:forEach var="idx" begin="0" end="${orders.size()-1}" step="1">
			<li>
				<strong><a href="/books/orders?id=${orders.get(idx).getId()}">Order ${orders.get(idx).getId()}</a></strong>
				for ${users.get(orders.get(idx).getUserId()).getName() }<br />
				${orders.get(idx).getPriceString() } for ${orders.get(idx).getQuantity() } books,
				arrival date: ${orders.get(idx).getDateString() }<br />
				Paid: <c:if test="${orders.get(idx).isPaid()}">Yes</c:if><c:if test="${!orders.get(idx).isPaid()}">No</c:if><br />
				Delivered: <c:if test="${orders.get(idx).isDelivered()}">Yes</c:if><c:if test="${!orders.get(idx).isDelivered()}">No</c:if>
			</li>
		</c:forEach>
	</ul>
	</c:if>
	
</body>
</html>