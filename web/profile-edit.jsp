<%@ page import="database.bean.User" %>
<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Edit Profile</title>

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	      integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">

	<!-- Bootstrap core JS -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
	        integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
	        crossorigin="anonymous"></script>

	<%
		User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);
	%>
</head>
<body>
<%@include file="imports/navbar.html" %>
<div class="container">
	<div class="page-header">
		<h1>Edit Profile</h1>
	</div>

	<div class="profile-edit">
		<div class="jumbotron">
			<h3>Enter your details</h3>

			<br>

			<form action="ProfileEdit" method="post">
				<div class="form-group">
					<label for="<%= ServletKey.FIRST_NAME%>">First Name</label>
					<input type="text" class="form-control" required autofocus name="<%= ServletKey.FIRST_NAME%>"
					       value=<%=currentUser.getFirstName()%>>
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.LAST_NAME%>">Last Name</label>
					<input type="text" class="form-control" required name="<%= ServletKey.LAST_NAME%>"
					       value=<%=currentUser.getLastName()%>>
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.COUNTRY%>">Country</label>
					<input type="text" class="form-control" required
					       name="<%= ServletKey.COUNTRY%>" value=<%=currentUser.getCountry()%>>
				</div>

				<div class="form-group">
					<label for="<%= ServletKey.PICTURE%>">Picture URL</label>
					<input type="url" class="form-control" name="<%= ServletKey.PICTURE%>">
				</div>
				<button type="submit" class="btn btn-primary" value="sign-up">Submit</button>
			</form>
		</div>
	</div>
</div>

</body>
</html>
