<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Arbuthnott's Books: Admin</title>
<%@ include file="/jsps/components/headerStuff.jsp" %>
</head>
<body>
	<%@ include file="/jsps/components/userNav.jsp" %>
	<h1>Arbuthnott's Books: Admin</h1>
	<hr />
	<p style="color:red">${adminMessage}</p>
	<h4><a href="/books/list">Book List</a></h4>
	<h4><a href="/books/admin/new">New Book</a></h4>
	<h3>Current Orders:</h3>
	<ul>
		<c:forEach var="idx" begin="0" end="${orders.size()-1}" step="1">
			<li>
				<strong><a href="/books/admin?order=${orders.get(idx).getId()}">Order ${orders.get(idx).getId()}</a></strong>
				for ${users.get(orders.get(idx).getUserId()).getName() }<br />
				${orders.get(idx).getPriceString() } for ${orders.get(idx).getQuantity() } books,
				arrival date: ${orders.get(idx).getDateString() }<br />
				Paid: <c:if test="${orders.get(idx).isPaid()}">Yes</c:if><c:if test="${!orders.get(idx).isPaid()}">No</c:if><br />
				Delivered: <c:if test="${orders.get(idx).isDelivered()}">Yes</c:if><c:if test="${!orders.get(idx).isDelivered()}">No</c:if>
			</li>
		</c:forEach>
	</ul>
	
</body>
</html>