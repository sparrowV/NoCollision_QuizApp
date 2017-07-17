<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>
		Quiz Website
	</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

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
				<p><img src="user.png" alt="Site Logo"> Tokoko</p>
				<button class="btn btn-primary">View Profile</button>
			</div>
		</div>
		<div class="col-lg-6">
			<div class="wrapper">
				<p>Category > Sport</p>
				<hr style="color: black">
				<h2>The History of the United States of America</h2>
				<hr style="color: black">
			</div>
		</div>
		<div class="col-lg-4">
			<div class="wrapper">
				<button class="btn btn-warning">Practice Mode</button>
				<br>
				<button class="btn btn-primary">Take Quiz</button>
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
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
						<tr>
							<td>1</td>
							<td>Username</td>
							<td>Georgia</td>
							<td>168.5</td>
						</tr>
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
			It is a long established fact that a reader will be distracted by the readable content of a page when
			looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of
			letters, as opposed to using 'Content here, content here', making it look like readable English. Many
			desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a
			search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved
			over the years, sometimes by accident, sometimes on purpose (injected humour and the like).


		</div>

	</div>
</div>

</body>
</html>

