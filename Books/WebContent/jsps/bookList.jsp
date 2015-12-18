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
	<h1>Book List Page</h1>
	<h2>
		${subtitle}
		<c:if test="${user != null }">
		<c:if test="${user.isAdmin() }">
			<form style="display: inline-block; margin-left: 20px;" method="get" action="/books/admin/new">
				<button type="submit" class="btn btn-primary">Admin: Add a Book to Inventory</button>
			</form>
		</c:if>
		</c:if>
	</h2>
	<hr />
	<p style="color:red">${adminMessage}</p>
	<ul>
		<c:forEach var="idx" begin="0" end="${books.size()-1}" step="1">
			<h4><a href="/books?id=${books.get(idx).getId()}">${books.get(idx).getTitle()}</a></h4>
			<p style="margin-top: -10px;">by <a href="/books/list?auth=${books.get(idx).getAuthorAlias() }">${books.get(idx).getAuthor() }</a>
		</c:forEach>
	</ul>

</body>
</html>