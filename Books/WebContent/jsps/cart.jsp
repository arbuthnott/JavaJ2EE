<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Book Cart</title>
<%@ include file="/jsps/components/headerStuff.jsp" %>
</head>
<body>
	<%@ include file="/jsps/components/userNav.jsp" %>
	<h1>Cart for ${user.getName() }</h1>
	<hr />
	<p style="color:red;">${cartMessage}</p>
	<p><a href="/books/list">Back to Book List</a></p>
	<c:choose>
		<c:when test="${cartOrder.getItems().size() == 0}">
			<p>Cart is currently empty!</p>
		</c:when>
		<c:otherwise>
			<ul>
				<c:forEach var="idx" begin="0" end="${cartOrder.getItems().size() - 1 }">
					<li>
						<strong>${books.get(cartOrder.getItems().get(idx).getBookId()).getTitle()}:</strong>
						${cartOrder.getItems().get(idx).getQuantity()} copies, 
						${cartOrder.getItems().get(idx).getPriceString()}
					</li>
				</c:forEach>
			</ul>
			<p>
				<strong>Total: ${cartOrder.getPriceString()}</strong>,
				order could be ready by <strong>${cartOrder.getDateString()}</strong>
			</p>
			<form method="post" action="cart" class="form-inline">
				<button type="submit" class="btn btn-primary" name="clearCart" value="1">Clear Cart</button>
			</form>
			<form method="post" action="cart" class="form-inline">
				<button type="submit" class="btn btn-primary" name="placeOrder" value="1">Place Order</button>
			</form>
			
		</c:otherwise>
	</c:choose>
	
</body>
</html>