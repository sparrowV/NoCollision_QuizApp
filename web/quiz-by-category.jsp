<%@ page import="model.QuizManager" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="database.bean.Quiz" %>
<%@ page import="model.FriendshipManager" %>
<%@ page import="database.bean.User" %>
<%@ page import="servlet.ServletKey" %>
<%--
  Created by IntelliJ IDEA.
  User: janxo
  Date: 7/18/2017
  Time: 6:31 PM
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>

	<%
		QuizManager quizManager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
		FriendshipManager friendshipManager = (FriendshipManager) application.getAttribute(ContextKey.FRIENDSHIP_MANAGER);
		User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);

		int categoryId = Integer.parseInt(request.getParameter(ServletKey.ID));
	%>
	<title>Category - <%= quizManager.getCategoryById(categoryId).getCategoryName()%></title>

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
	<div class="quiz-by-category">
		<div class="jumbotron">
			<label for="quizzes">Quizzes</label>
			<div id="quizzes">
				<div class="table-responsive">
					<table class="table">
						<thead class="thead-inverse">
						<tr>
							<th>ID</th>
							<th>Title</th>
							<th>Issue Date</th>
							<th>Delete</th>
						<tr>
						</thead>
						<tbody>
						<%
							for (Quiz quiz : quizManager.getQuizByCategoryId(categoryId)) {
								out.print(quiz.toHtml(friendshipManager.getFriends(currentUser.getUserId())));
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
