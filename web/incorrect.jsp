<%@ page import="servlet.ServletKey" %>
<%@ page import="database.bean.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Information Incorrect</title>
	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	      integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">

	<!-- Bootstrap core JS -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
	        integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
	        crossorigin="anonymous"></script>
	<%
		User checkUser = (User) session.getAttribute(ServletKey.CURRENT_USER);
		if (checkUser != null) {
			response.sendRedirect(ServletKey.HOME_PAGE_JSP);
			return;
		}%>
</head>


<body>

<div class="container">
	<div class="page-header">
		<h4>Please Try Again</h4>
		<h>Either username or password is incorrect</h>
	</div>

	<div class="login">
		<div class="jumbotron">
			<h3>Please log in</h3>

			<br>

			<form action="Login" method="post">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Username" required autofocus
					       name="<%= ServletKey.USERNAME%>">
				</div>

				<div class="form-group">
					<input type="password" class="form-control" placeholder="Password" required
					       name="<%= ServletKey.PASSWORD%>">
				</div>

				<button type="submit" class="btn btn-primary" value="login">Login</button>
			</form>

		</div>

		<a href="${pageContext.request.contextPath}/<%= ServletKey.SIGN_UP_JSP%>">Create New Account</a>
	</div>
</div>
</body>


</html>
