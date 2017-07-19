<%@ page import="database.bean.*" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.*" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.List" %>
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
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
	<script type='text/javascript'>
		$(document).ready(function () {
			$("[data-toggle=tooltip]").tooltip();
		});
	</script>

	<script src="/imports/scripts.js" type="text/javascript"></script>

</head>
<body>
<%@include file="imports/header.jsp" %>

<%
	AnnouncementManager announcementManager = (AnnouncementManager) application.getAttribute(ContextKey.ANNOUNCEMENT_MANAGER);
	Announcement announcement = announcementManager.getLastAnnouncement();
	TimelineManager timelineManager = (TimelineManager) application.getAttribute(ContextKey.TIMELINE_MANAGER);
	List<TimelineActivity> timeline = timelineManager.getTimeline(friendshipManager.getFriends(currentUser.getUserId()));
%>

<div class="container">
	<div class="home-page">
		<div class="jumbotron">
			<td>

				<p><a class="btn btn-default bg-info"
				      href="${pageContext.request.contextPath}/<%= ServletKey.CREATE_QUIZ_JSP%>">Create New Quiz</a>
				</p>

				<div class="dropdown">
					<button class="btn btn-default">Select Quiz By Category</button>
					<div class="dropdown-content">
						<%
							QuizManager quizManager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
							List<Category> categories = quizManager.getQuizCategories();
							for (int i = 0; i < categories.size(); i++) {
								Category currCategory = categories.get(i);
								out.print("<a href=" + ServletKey.QUIZ_BY_CATEGORY_JSP + "?id="
										+ currCategory.getCategoryId() + ">" + currCategory.getCategoryName() + "</a>\n");
							}
						%>

					</div>
				</div>

					<%
				//displaying all quizzes for given user
				User user = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);
				int userId = user.getUserId();
				QuizManager manager = (QuizManager) request.getServletContext().getAttribute(ContextKey.QUIZ_MANAGER);

				List<Quiz> quizzes = manager.getQuizzesByAuthorId(userId);
				out.write("<div id='quizzes'>\n");
				out.write("<h3>My Quizzes</h3>\n");
				for (Quiz quiz : quizzes) {
					out.write(quiz.toHtml(myFriends));
					out.write("<br>\n");

				}
				out.write("</div>\n");
			%>

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
						"      <h1>" + announcement.getAnnouncement() + "</h1>\n" +
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
</div>
</body>
</html>
