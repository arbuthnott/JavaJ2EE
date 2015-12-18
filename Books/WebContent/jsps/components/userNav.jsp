<nav class="navbar navbar-default">
	<div class="navbar-header">
		<a class="navbar-brand" href="/books">Arbuthnott's Books</a>
	</div>
	<ul class="nav navbar-nav">
		<li><a href="/books">Home</a></li>
        <li><a href="/books/list">All Books</a></li>
    </ul>
<c:choose>
	<c:when test="${user != null }">
		<c:if test="${user.isAdmin()}">
			<ul class="nav navbar-nav">
				<li><a href="/books/admin">Admin Home</a></li>
			</ul>
		</c:if>
		<ul class="nav navbar-nav navbar-right" style="padding-right:15px;">
			<li><a href="/books/profile">My Profile</a></li>
			<li><a href="/books/cart">My Cart</a></li>
			<li><a href="/books/login?action=logout">Logout</a></li>
		</ul>
	</c:when>
	<c:otherwise>
		<ul class="nav navbar-nav navbar-right" style="padding-right:15px;">
			<li><a href="/books/login">Log In</a></li>
		</ul>
	</c:otherwise>
</c:choose>

</nav>

<c:if test="${user != null }">
	<p style="display: inline-block; float: right; line-height: 0.8em; margin-top: -15px;">${user.getName() }</p>
</c:if>