<%@ page import="listener.ContextKey" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="model.*" %>
<%@ page import="database.bean.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Quiz Website</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<!-- Bootstrap core JS and CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
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

		List<User> myFriends = friendshipManager.getFriends(currentUser.getUserId());

		ChallengeManager challengeManager = (ChallengeManager) application.getAttribute(ContextKey.CHALLENGE_MANAGER);
		ArrayList<Challenges> myChallenges = challengeManager.getMyChallenges(currentUser.getUserId());
		String challengeTitle = "Challenges";
		if (myChallenges.size() != 0) {
			challengeTitle = "Challenges (" + myChallenges.size() + ")";
		}

		AnnouncementManager announcementManager = (AnnouncementManager) application.getAttribute(ContextKey.ANNOUNCEMENT_MANAGER);
		Announcement announcement = announcementManager.getLastAnnouncement();
		TimelineManager timelineManager = (TimelineManager) application.getAttribute(ContextKey.TIMELINE_MANAGER);
		List<TimelineActivity> timeline = timelineManager.getTimeline(friendshipManager.getFriends(currentUser.getUserId()));
	%>
	<%--copied from w3schools material--%>
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
</head>
<body>

<%@include file="imports/header.jsp" %>

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
					out.write(quiz.toHtml(myFriends));

				}
				out.write("</div>");
			%>

			<p><a class="btn btn-default bg-info"
			      href="${pageContext.request.contextPath}/<%= ServletKey.CREATE_QUIZ_JSP%>">Create New Quiz</a></p>
		</div>
	</div>


</div>

<div class="w3-container">

	<%--announcement--%>
	<%
		if (announcement != null) {
			out.write("  <div class=\"w3-card-4\" style=\"width:50%; text-align: center; margin: auto;\">\n" +
					"    <header class=\"w3-container w3-orange\">\n" +
					"      <h1>Admin Announcement</h1>\n" +
					"    </header>\n" +
					"    <div class=\"w3-container\">\n" +
					"      <hr>\n" +
					"      <h1>" + announcement.getAnnouncement() + "</h1>" +
					"<br>\n" +
					"    </div>\n" +
					"  </div>\n" +
					"<br>\n" +
					"<br>\n");
		}
	%>


	<% // friends activity
		for (int i = 0; i < timeline.size(); i++) {
			out.write("<div class=\"w3-card-4\" style=\"width:50%; text-align: center; margin: auto;\">\n" +
					"\t\t<header class=\"w3-container w3-cyan\">\n");
			out.write("<h1> <b>" + timeline.get(i).getUserName() + "<b><h1>\n");
			out.write("\t\t</header>\n");

			out.write("\t\t<div class=\"w3-container\">\n");
			out.write("<h1> Quiz: " + timeline.get(i).getQuizTitle() + "<h1>\n");
			out.write("<h2>Score: " + timeline.get(i).getScore() + "<h2>\n");
			out.write("<h2>XP: " + timeline.get(i).getXp() + "<h2>\n");
			out.write("<h2>Time: " + timeline.get(i).getDuration() + "<h2>\n");

			out.write("\t\t</div>\n");


			out.write("\t</div>\n");
			if (i != timeline.size() - 1) {
				out.write("<br>\n");
				out.write("<br>\n");
			}
		}

	%>

</div>

<script language="javascript">
	function logOut() {
		$('<form>', {
			'action': 'Logout',
			'method': 'post'
		}).appendTo(document.body).submit().remove();
	}

	function sendChallenge(quiz_id, friend_id) {
		xhr2 = new XMLHttpRequest();

		var url = "/SendChallenge?quiz_id=" + quiz_id + "&friend_id=" + friend_id;

		xhr2.onreadystatechange = handler2;
		xhr2.open("POST", url, true);
		xhr2.send(null);
	}

	function handler2() {
		if (xhr2.readyState === 4) {
			if (xhr2.status === 200) {
				console.log("successful");
				alert("Challenge  sent")
			} else {
				alert("ERROR");
			}
		}
	}
</script>


</body>
</html>
