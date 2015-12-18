<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Order</title>
<%@ include file="/jsps/components/headerStuff.jsp" %>
<style>form {display: inline-block; margin-right: 20px;}</style>
</head>
<body>
	<%@ include file="/jsps/components/userNav.jsp" %>
	<h1>Order Details</h1>
	<hr />
	<p style="color:red;">${adminMessage}</p>
	<c:choose>
		<c:when test="${user != null && user.isAdmin()}">
			<p><a href="/books/admin">Back to Admin Home</a></p>
		</c:when>
		<c:otherwise>
			<p><a href="/books/profile">Back to my Profile</a></p>
		</c:otherwise>
	</c:choose>
	<h3>Order ${order.getId()}</h3>
	<p>
		Order for ${users.get(order.getUserId()).getName() }<br />
		Total cost:${order.getPriceString()} for ${order.getQuantity() } books:<br />
		<ul>
			<c:forEach var="idx" begin="0" end="${order.getItems().size() - 1}">
				<li>
					${order.getItems().get(idx).getQuantity()} copies of
					${books.get(order.getItems().get(idx).getBookId()).getTitle()} for
					${order.getItems().get(idx).getPriceString()}
				</li>
			</c:forEach>
		</ul>
		Arrival date: ${order.getDateString() }<br />
		Paid: <c:if test="${order.isPaid()}">Yes</c:if><c:if test="${!order.isPaid()}">No</c:if><br />
		Delivered: <c:if test="${order.isDelivered()}">Yes</c:if><c:if test="${!order.isDelivered()}">No</c:if>
	</p>
	<c:if test="${user != null && user.isAdmin()}">
		<c:if test="${!order.isPaid()}">
			<form method="post" action="/books/admin">
				<input type="hidden" name="orderId" value="${order.getId()}" />
				<button type="submit" class="btn btn-primary" name="updateOrder" value="paid">
					Mark as Paid
				</button>
			</form>
		</c:if>
		<c:if test="${!order.isDelivered()}">
			<form method="post" action="/books/admin">
				<input type="hidden" name="orderId" value="${order.getId()}" />
				<button type="submit" class="btn btn-primary" name="updateOrder" value="delivered">
					Mark as Delivered
				</button>
			</form>
		</c:if>
		<form method="post" action="/books/admin">
			<input type="hidden" name="orderId" value="${order.getId()}" />
			<button type="submit" class="btn btn-primary" name="updateOrder" value="delete">
				Delete Order Record
			</button>
		</form>
	
	</c:if>

</body>
</html>