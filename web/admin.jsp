<%@ page import="database.bean.Quiz" %>
<%@ page import="database.bean.User" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.QuizManager" %>
<%@ page import="model.UserManager" %>
<%@ page import="servlet.ServletKey" %>
<%--
  Created by IntelliJ IDEA.
  User: janxo
  Date: 7/11/2017
  Time: 6:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Admin</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

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
	<div class="admin">
		<div class="jumbotron">
			<div id="announcement">
				<!-- create button when pressed creates text area and another button for submitting text -->
				<button class="btn btn-default" id="button-announcement" onclick="createAnnouncement()">Create
					Announcement
				</button>
				<br>
				<script>
					var $textArea = $("<textarea>", {id: "text", class: "form-control", rows: 2, cols: 50});
					var $button = $("<button>", {
						id: "button-addAnnouncement",
						class: "btn btn-primary",
						text: "Add Announcement"
					});
					$("#announcement").append($textArea);
					$("#announcement").append($button);

					$("#text").hide();
					$("#button-addAnnouncement").hide();

					function createAnnouncement() {
						$("#text").show();
						$("#button-addAnnouncement").show();
						$button.click(sendAnnouncement);
						// get text from text area and send to servlet
						function sendAnnouncement() {
							// hide buttons
							$("#text").hide();
							$("#button-addAnnouncement").hide();
							// clear text area
							$("#text").val("");

							// send announcement to servlet
							var text = {text: $textArea.val()};
							$.ajax({
								url: '/AnnouncementServlet',
								type: 'POST',
								data: JSON.stringify(text),
								contentType: 'application/json; charset=utf-8',
								dataType: 'json',
								async: true,
								success: function (msg) {
									alert(msg);
								}
							});
						}

					}
				</script>
			</div>

			<br>

			<label for="users">Users</label>
			<div id="users">
				<div class="table-responsive">
					<table class="table">
						<thead class="thead-inverse">
						<tr>
							<th>ID</th>
							<th>Username</th>
							<th>Birth date</th>
							<th>Delete</th>
							<th>Change status</th>
						<tr>
						</thead>
						<tbody>
						<%
							User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);
							UserManager userManager = (UserManager) application.getAttribute(ContextKey.USER_MANAGER);
							for (User user : userManager.getUserList()) {
								if (!user.equals(currentUser)) {
									out.print(user.toHtmlTableFormat());
								}
							}
						%>
						</tbody>
					</table>
				</div>
			</div>

			<br>
			<br>

			<label for="quizzes">Quizzes</label>
			<div id="quizzes">
				<div class="table-responsive">
					<table class="table">
						<thead class="thead-inverse">
						<tr>
							<th>ID</th>
							<th>Title</th>
							<th>Date</th>
							<th>Delete</th>
						<tr>
						</thead>
						<tbody>
						<%
							QuizManager quizManager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
							for (Quiz quiz : quizManager.getQuizList()) {
								out.print(quiz.toHtml());
							}
						%>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>
