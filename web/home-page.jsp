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

	<script language="javascript">
		function doPost() {
			$('<form>', {
				'action': 'Logout',
				'method': 'post'
			}).appendTo(document.body).submit().remove();
		}
	</script>

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<!-- Bootstrap core JS and CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<script type='text/javascript'>
		$(document).ready(function () {
			$("[data-toggle=tooltip]").tooltip();
		});
	</script>

	<%
		FriendshipManager friendshipManager = (FriendshipManager) application.getAttribute(ContextKey.FRIENDSHIP_MANAGER);
		User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);
		if (currentUser == null) {
			response.sendRedirect("/index.jsp");
			return;
		}
		List<User> friendRequests = friendshipManager.getReceivedFriendRequests(currentUser.getUserId());
		String friendRequestNotification = "";
		if (!friendRequests.isEmpty()) {
			friendRequestNotification = " (" + friendRequests.size() + ")";
		}
	%>
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
				<li><a href="${pageContext.request.contextPath}/MyFriends">My Friends</a></li>

				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
					   aria-expanded="false">Friend Requests<%=friendRequestNotification%> <span
							class="caret"></span></a>
					<ul class="dropdown-menu">
						<%
							if (friendRequests.size() == 0) {
								out.write("<li class=\"dropdown-header\">No friend requests</li>");
							}
							for (int i = 0; i < friendRequests.size(); i++) {
								out.write("<li><b>" + friendRequests.get(i).getUsername() + "</b>\n" +
										"<form action=\"/FriendRequestResponse\" method=\"post\" onsubmit=\"acceptRequest(1); return false;\">\n" +
										"   <input id=\"accept\" name=\"friend_id\" type=\"hidden\" value=\"" + friendRequests.get(i).getUserId() + "\"/>" +
										" <input type=\"submit\" class=\"btn btn-default\" value=\"Accept\"/>" +
										"</form>" +
										"<form action=\"/FriendRequestResponse\" method=\"post\" onsubmit=\"acceptRequest(0); return false;\">\n" +
										"   <input id=\"reject\" name=\"friend_id\" type=\"hidden\" value=\"" + friendRequests.get(i).getUserId() + "\"/>" +
										" <input type=\"submit\" class=\"btn btn-default\" value=\"Reject\"/>" +
										"</form>" +
										"  </li>\n");
								if (i != friendRequests.size() - 1)
									out.write("<li role='separator' class='divider'></li>\n");
							}%>

						<script> function acceptRequest(control) {
							try {
								xhr = new XMLHttpRequest();
							} catch (e) {
								xhr = new ActiveXObject("Microsoft.XMLHTTP");
							}
							if (xhr === null) {
								alert("Ajax not supported by your browser!");
								return;
							}
							if (control === 1) {
								var url = "FriendRequestResponse?status=1&friend_id=" + document.getElementById("accept").value;
							}
							if (control === 0) {
								var url = "FriendRequestResponse?status=0&friend_id=" + document.getElementById("reject").value;
							}

							xhr.onreadystatechange = handler;
							xhr.open("POST", url, true);
							xhr.send(null);
						}


						function handler() {
							if (xhr.readyState === 4) {
								if (xhr.status === 200) {
									console.log("successful");
									location.reload();
								} else {
									alert("ERROR");
								}
							}
						}
						</script>

					</ul>
				</li>

				<form class="navbar-form navbar-left" action="search.jsp" method="post">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search" name="<%= ServletKey.SEARCH%>">>
					</div>
					<button type="submit" class="btn btn-default" data-toggle="tooltip" data-placement="bottom"
					        data-html="true" title="user:<i>username</i> for users<br>quiz:<i>title</i> for quizzes">
						Submit
					</button>
				</form>

			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
					   aria-expanded="false"><%= currentUser.getFirstName() + " " + currentUser.getLastName()%><span
							class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="${pageContext.request.contextPath}/user/<%= currentUser.getUserId()%>">Profile</a>
						</li>
						<li><a href="javascript:doPost()">Log out</a></li>
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
					out.write("<a href=" + ServletKey.DO_QUIZ_JSP + "?id=" + quiz.getQuizId() + "></a>");
				}
				out.write("</div>");
			%>
			<p><a href="${pageContext.request.contextPath}/<%= ServletKey.CREATE_QUIZ_JSP%>">Create New Quiz</a></p>
		</div>
	</div>
</div>
</body>
</html>
