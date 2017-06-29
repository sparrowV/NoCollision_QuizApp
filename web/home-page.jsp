<%@ page import="database.bean.Quiz" %>
<%@ page import="database.bean.User" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.FriendshipManager" %>
<%@ page import="model.QuizManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>
		Quiz Website
	</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- JQuery -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<script language="javascript">
        function DoPost() {
            $.post("Logout", {"logout": "logout"})
        }
	</script>

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<!-- Bootstrap core JS and CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<% FriendshipManager friendshipManager = (FriendshipManager) application.getAttribute(ContextKey.FRIENDSHIP_MANAGER);
		User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);
		List<User> friendRequests = friendshipManager.getReceivedFriendRequests(String.valueOf(currentUser.getUserId()));
		String friendRequestNotification = "";
		if (friendRequests.size() != 0) {
			friendRequestNotification = " (" + friendRequests.size() + ")";
		}%>
</head>
<body>

<!-- Fixed navbar -->
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
			        aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">Quiz Website</a>
		</div>
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#about">About</a></li>
				<li><a href="#contact">Contact</a></li>
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
					   aria-expanded="false">Friend Requests<%=friendRequestNotification%> <span
							class="caret"></span></a>
					<ul class="dropdown-menu">
						<%--href is missing
						 profile?id=profile_id --%>
						<%
							if (friendRequests.size() == 0) {
								out.write("<li class=\"dropdown-header\">No friend requests</li>");
							}
							for (int i = 0; i < friendRequests.size(); i++) {
								out.write("<li> <a href='#'>" + friendRequests.get(i).getFirstName() + " " +
										friendRequests.get(i).getLastName() + " " +
										"<b>" + friendRequests.get(i).getUsername() +
										"</b>  <a  href='#'>Accept</a>  <a href='#'>Reject</a>  </a>  </li>");
								if (i != friendRequests.size() - 1)
									out.write("<li role='separator' class='divider'></li>");
							}%>
					</ul>
				</li>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
					   aria-expanded="false"><%= currentUser.getFirstName() + " " + currentUser.getLastName()%><span
							class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/user/<%= currentUser.getUserId()%>">Profile</a>
						</li>
						<li><a href="javascript:DoPost()">Log out</a></li>
					</ul>
				</li>
			</ul>
		</div><!--/.nav-collapse -->
	</div>
</nav>

<div class="container">
	<div class="home-page">
		<div class="jumbotron">
			<%
				//displaying all quizzes for given user
				User user = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);
				int userId = user.getUserId();
				QuizManager manager = (QuizManager) request.getServletContext().getAttribute(ContextKey.QUIZ_MANAGER);

				List<Quiz> quizzes = manager.getQuizzesByAuthorId(userId);
				out.write("<div id='quizzes'>");
				for (Quiz quiz : quizzes) {
					out.write(quiz.toHtml());
					out.write("<a href=" + ServletKey.DO_QUIZ_JSP + "?id=" + quiz.getQuizId() + ">" + "Do Quiz" + "</a>");
				}
				out.write("</div>");
			%>
			<p><a href="${pageContext.request.contextPath}/<%= ServletKey.CREATE_QUIZ_JSP%>">Create New Quiz</a></p>
		</div>
	</div>
</div>
</body>
</html>
