<%@ page import="database.bean.User" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.UserManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Search results</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<!-- Bootstrap core JS and CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="javascript:history.back()">Back</a>
		</div>
	</div>
</nav>

<div class="container">
	<div class="search-results">
		<div class="jumbotron">
			<%
				String query = request.getParameter(ServletKey.SEARCH);

				// Check for the prefix keyword.
				if (query.startsWith(ServletKey.SEARCH_PREFIX_USER)) {
					UserManager userManager = (UserManager) request.getServletContext().getAttribute(ContextKey.USER_MANAGER);

					// Filter query, remove prefix.
					String filtered = query.substring(ServletKey.SEARCH_PREFIX_USER.length());

					// Get user.
					User user = userManager.getUser(filtered);

					if (user != null) {
						// If the username exists, redirect to her page.
						response.sendRedirect("/user/" + user.getUserId());
					} else {
						// Print that no users where found.
						out.write("<p> No users found with that username. </p>");
					}
				} else if (query.startsWith(ServletKey.SEARCH_PREFIX_QUIZ)) {
					System.out.println("Quiz search");
					// TODO
				} else {
					System.out.println("Everything else");
					// TODO
				}
			%>
		</div>
	</div>
</div>

</body>
</html>