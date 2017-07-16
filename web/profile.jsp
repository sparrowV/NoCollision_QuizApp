<%@ page import="database.bean.Quiz" %>
<%@ page import="database.bean.User" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.FriendshipManager" %>
<%@ page import="model.QuizManager" %>
<%@ page import="model.UserManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.List" %>
<%@ page import="model.BadgeManager" %>
<%@ page import="database.bean.Badge" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script src="/imports/scripts.js" type="text/javascript"></script>

	<style>

		.dropdown {
			position: relative;
			display: inline-block;
		}

		.dropdown-content {
			display: none;
			position: absolute;
			background-color: #f9f9f9;
			min-width: 160px;
			box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
			z-index: 1;
		}

		.dropdown-content a {
			color: black;
			padding: 12px 16px;
			text-decoration: none;
			display: block;
		}

		.dropdown-content a:hover {
			background-color: #f1f1f1
		}

		.dropdown:hover .dropdown-content {
			display: block;
		}

		.dropdown:hover .dropbtn {
			background-color: #3e8e41;
		}</style>

	<%
		UserManager userManager = (UserManager) application.getAttribute(ContextKey.USER_MANAGER);
		FriendshipManager friendshipManager = (FriendshipManager) application.getAttribute(ContextKey.FRIENDSHIP_MANAGER);
		BadgeManager badgeManager = (BadgeManager) application.getAttribute(ContextKey.BADGE_MANAGER);

		// temporary code for filtering
		int userId = Integer.parseInt((String) request.getAttribute("id"));
		User user = userManager.getUserById(userId);
		User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);

		boolean isFriendAlready = friendshipManager.areFriends(currentUser.getUserId(), user.getUserId());
		if (user == null)
			user = (User) session.getAttribute(ServletKey.CURRENT_USER);

		QuizManager quizManager = (QuizManager) request.getServletContext().getAttribute(ContextKey.QUIZ_MANAGER);
		List<Quiz> quizzes = quizManager.getQuizzesByAuthorId(user.getUserId());
		String pictureUrl = user.getPicture();
		if (pictureUrl == null || pictureUrl.isEmpty()) {
			pictureUrl = "resources/profile.PNG";
		}
	%>
	<title><%=user.getFirstName() + " " + user.getLastName()%>
	</title>


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
	<div class="profile">
		<div class="jumbotron">
			<div class="page-header">
				<h4><%=user.getFirstName() + " " + user.getLastName()%>
				</h4>
				<div class="profile-picture">
					<img src="<%= pictureUrl%>" class="img-thumbnail"
					     height="450" width="450"
					     alt="<%=user.getFirstName() + " " + user.getLastName()%>">
				</div>
			</div>
			<br>
			<div class="profile-info">
				<p><strong>Username: </strong><%=user.getUsername()%>
				</p>
				<p><strong>Gender: </strong><%=user.getGender()%>
				</p>
				<p><strong>Country: </strong><%=user.getCountry()%>
				</p>
				<p><strong>Date of Birth: </strong><%=user.getDateOfBirth().toString()%>
				</p>

				<%
					List<Badge> badges = badgeManager.getBadgesByUserId(userId);
					for (Badge badge : badges) {
						out.write(badge.toString());
						out.write("<br>");
					}
				%>


				<% if (currentUser.getUserId() != userId && (!isFriendAlready)) {
					out.println("<button class=\"btn btn-default\" onclick=\"sendRequest(" + userId +
							")\">Add Friend</button>\n");
				} else if (currentUser.getUserId() == userId) {
					out.println("<button class=\"btn btn-default\"  onclick=\"window.location = '/profile-edit.jsp';\">Edit Profile</button>\n");
				}%>
			</div>

			<%
				// Create responsive table div.
				out.println("<div class=\"table-responsive\">");

				// Print table header.
				out.println("<table class=\"table\">\n" +
						"  <thead class=\"thead-inverse\">\n" +
						"    <tr>\n" +
						"      <th>ID</th>\n" +
						"      <th>Title</th>\n" +
						"      <th>Date Created</th>\n" +
						"    </tr>\n" +
						"  </thead>\n" +
						"  <tbody>");

				// Print each quiz's html representation.
				for (Quiz quiz : quizzes) {
					out.print(quiz.toHtml(friendshipManager.getFriends(currentUser.getUserId())));
				}

				// Print table footer.
				out.println("  </tbody>\n" +
						"</table>");

				// Print div closing tag.
				out.println("</div>");
			%>
		</div>
	</div>
</div>
</body>
</html>
