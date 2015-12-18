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
	<h1>Book Detail Page</h1>
	<hr />
	<p style="color:red">${adminMessage}</p>
	<p><a href="/books/list">Back to Book List</a></p>
	<h2>
		<strong>${ book.getTitle() }</strong>
		<c:if test="${user != null }">
		<c:if test="${user.isAdmin() }">
			<form style="display: inline-block; margin-left: 20px;" method="get" action="/books/admin">
				<button type="submit" class="btn btn-primary" name="book" value="${book.getId()}">Admin: Edit Inventory</button>
			</form>
		</c:if>
		</c:if>
	</h2>
	<p>
		by <a href="/books/list?auth=${book.getAuthorAlias() }">${book.getAuthor() }</a><br />
		Retails for ${book.getPriceString(1) }<br />
		${book.getLocalStock() } available in store archive<br />
		${book.getRemoteStock() } available for order from remote storage
	</p>
	<c:choose>
		<c:when test="${username != 'Guest'}">
			<form method="post" action="/books/cart" class="form-inline">
				<select name="quantity">
					<c:forEach var="idx" begin="1" end="${book.getLocalStock() + book.getRemoteStock() }" step="1">
						<option 
							<c:if test="{${idx == 1 }"> selected </c:if>
							value="${idx }">${idx }
						</option>
					</c:forEach>
				</select>
				<label>copies</label>
				<button class="btn btn-primary" type="submit" name="buyBook" value="${book.getId()}">Add to Cart</button>
			</form>
		</c:when>
		<c:otherwise>
			<p><a href="/books/login">Log in</a> to add items to your cart!</p>
		</c:otherwise>
	</c:choose>
</body>
</html>