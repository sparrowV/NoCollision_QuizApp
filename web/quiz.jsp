<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<%
		QuizManager quizManager = (QuizManager) application.getAttribute(ContextKey.QUIZ_MANAGER);
		UserManager userManager = (UserManager) application.getAttribute(ContextKey.USER_MANAGER);
		LeaderboardManager leaderboardManager = (LeaderboardManager) application.getAttribute(ContextKey.LEADERBOARD_MANAGER);
		int quizId = Integer.parseInt((String) request.getAttribute("id"));
		Quiz quiz = quizManager.getQuizById(quizId);
		User author = userManager.getUserById(quiz.getAuthorId());
		Category category = quizManager.getCategoryById(quiz.getCategoryId());
	%>
	<title>
		<%= quiz.getTitle() %>
	</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="/style.css">

	<!-- Bootstrap core JS and CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">


	<script src="/imports/scripts.js" type="text/javascript"></script>

	<style>
		.wrapper {
			padding: 20px;
		}

		.wrapper button {
			margin: 5px;
			margin-left: 40px;
			width: 130px;
		}

		.table {
			border: 2px solid;
			border-radius: 20px;
			box-shadow: 1px 1px 15px black;
		}

		.row {
			background-color: white;
		}

		#table-wrapper {
			overflow: auto;
			height: 400px;
		}

		::-webkit-scrollbar {
			width: 12px;
		}

		::-webkit-scrollbar-track {
			-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
			border-radius: 10px;
		}

		::-webkit-scrollbar-thumb {
			border-radius: 10px;
			-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.5);
		}

	</style>
</head>
<body>
<%@include file="imports/header.jsp" %>

<div class="container bg-warning" id="main">
	<div class="row">
		<div class="col-lg-2">
			<div class="wrapper">
				<p>Created By:</p>
				<a href=<%="/user/" + author.getUserId()%>><p><%= author.getUsername() %>
				</p></a>
				<a href="<%=author.getProfilePath()%>">
					<button class="btn btn-primary">View Profile</button>
				</a>
			</div>
		</div>
		<div class="col-lg-6">
			<div class="wrapper">
				<p>Category > <%=category.getCategoryName()%>
				</p>
				<hr>
				<h2><%=quiz.getTitle()%>
				</h2>
				<hr>
			</div>
		</div>
		<div class="col-lg-4">
			<div class="wrapper">
				<a href="<%=quiz.getPath(true)%>">
					<button class="btn btn-warning">Practice Mode</button>
				</a>
				<br>
				<a href="<%=quiz.getPath(false)%>">
					<button class="btn btn-primary">Take Quiz</button>
				</a>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-lg-8">
			<div class="panel panel-default">
				<div class="panel-heading">Leaderboard</div>
				<div class="panel-body" id="table-wrapper">
					<table class="table table-striped">
						<tr>
							<th>Rank</th>
							<th>Username</th>
							<th>Country</th>
							<th>Score</th>
						</tr>
						<%
							Leaderboard board = leaderboardManager.getLeaderboardByQuizId(quizId);
							List<Leaderboard.Entry> entries = board.getEntries();
							for (int i = 0; i < board.size(); i++) {
								User user = entries.get(i).user;
								double score = entries.get(i).score;
								out.write("<tr>");
								out.write("<td>" + Integer.toBinaryString(i) + "</td>");
								out.write("<td>" + user.getUsername() + "</td>");
								out.write("<td>" + user.getCountry() + "</td>");
								out.write("<td>" + Double.toString(score) + "</td>");
								out.write("</tr>");
							}
						%>

					</table>
				</div>
			</div>
		</div>
		<div class="col-lg-4">
			<div class="panel panel-default">
				<div class="panel-heading">Related Quizzes</div>
				<ul class="list-group">
					<li class="list-group-item">First item</li>
					<li class="list-group-item">Second item</li>
					<li class="list-group-item">Third item</li>
				</ul>
			</div>
		</div>

	</div>
</div>

</body>
</html>

