<%@ page import="listener.ContextKey" %>
<%@ page import="model.AnnouncementManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="database.bean.User" %>
<%@ page import="database.bean.Announcement" %>
<%@ page import="model.UserManager" %>
<%@ page import="model.QuizManager" %>
<%@ page import="database.bean.Quiz" %><%--
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

	<style>
		.dropbtn {
			background-color: #4CAF50;
			color: white;
			padding: 16px;
			font-size: 16px;
			border: none;
			cursor: pointer;
		}

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
		}
	</style>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<div class="container">
	<div class="admin">
		<div id="announcement">
			<!-- create button when pressed creates text area and another button for submitting text -->
			<button id="button-announcement" onclick="createAnnouncement()">Create Announcement</button>
			<script>
				function createAnnouncement() {
					var $textArea = $("<textarea>", {id: "text", class: "announcement-text", rows: 2, cols: 50});
					var $button = $("<button>", {id: "button-addAnnouncement", text: "Add Announcement"});
					$button.click(sendAnnouncement);
					// get text from text area and send to servlet
					function sendAnnouncement() {
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

					$("#announcement").append($textArea);
					$("#announcement").append($button);
				}
			</script>
		</div>

		<br>

		<div class="users-list">
			<table class="user-table">
				<thead class="user-table-head">
				<tr>
					<th>ID</th>
					<th>UserName</th>
					<th>BirthDate</th>
				<tr>
				</thead>
				<tbody>
				<%
					UserManager userManager = (UserManager) application.getAttribute(ContextKey.USER_MANAGER);
					for (User user : userManager.getUserList()) {
						out.print(user.toHtmlTableFormat());
					}
				%>
				</tbody>
			</table>
		</div>
		<br>
		<br>
		<div class="quiz-list">
			<table class="quiz-table">
				<thead class="quiz-table-head">
				<tr>
					<th>ID</th>
					<th>Title</th>
					<th>Date</th>
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
</body>
</html>
