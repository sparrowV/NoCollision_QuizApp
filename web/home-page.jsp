<%@ page import="database.bean.Challenges" %>
<%@ page import="database.bean.Quiz" %>
<%@ page import="database.bean.User" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.ChallengeManager" %>
<%@ page import="model.FriendshipManager" %>
<%@ page import="model.QuizManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.ArrayList" %>
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
		request.setCharacterEncoding("UTF-8");
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

				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
					   aria-expanded="false">Messages<span
							class="caret"></span></a>
					<ul class="dropdown-menu">


						<%


							if (myFriends.size() == 0) {
								out.write("<li class=\"dropdown-header\">No Mails Yet</li>\n");
							}
							for (int i = 0; i < myFriends.size(); i++) {
								out.write("<li>\n" +
										" <form  action=\"mail.jsp\" method=\"post\">" +
										"<input type=\"submit\" value=\"" + myFriends.get(i).getFirstName() + " " + myFriends.get(i).getLastName() + "\"/>\n" +
										"    <input name=\"friend_id\" value=\"" + myFriends.get(i).getUserId() + "\" type=\"hidden\"/>\n" +
										"    <input name=\"friend_name\" value=\"" + myFriends.get(i).getFirstName() + " " + myFriends.get(i).getLastName() + "\" type=\"hidden\"/>\n" +
										" </form></li>\n");
								if (i != myFriends.size() - 1)
									out.write("<li role='separator' class='divider'></li>\n");
							}%>


					</ul>
				</li>

				<li class="dropdown">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
					   aria-expanded="false"><%=challengeTitle%><span
							class="caret"></span></a>

					<ul class="dropdown-menu">

						<%
							if (myChallenges.size() == 0) {
								out.write("<li class=\"dropdown-header\">No quiz challenges</li>");
							}

							for (int i = 0; i < myChallenges.size(); i++) {
								Challenges currChallenge = myChallenges.get(i);
								//do-quiz.jsp?id=
								out.write("<li>" +
										" <b>" + currChallenge.getChallengerUsername() + "</b>" +
										" <a  onclick=\"acceptedChallenge(" + currChallenge.getChallengedQuizID() + "," + currChallenge.getChallengerID() +
										")\" href=\"do-quiz.jsp?id=" + currChallenge.getChallengedQuizID() + "\">" + currChallenge.getChallengedQuizTitle() + "</a>"
										+ "</li>");
								if (i != myChallenges.size() - 1)
									out.write("<li role='separator' class='divider'></li>\n");
							}
						%>
						<script>
							function acceptedChallenge(quiz_id, challenger_id) {
								console.log(quiz_id);
								console.log(challenger_id);
								try {
									xhr1 = new XMLHttpRequest();
								} catch (e) {
									xhr1 = new ActiveXObject("Microsoft.XMLHTTP");
								}
								if (xhr1 === null) {
									alert("Ajax not supported by your browser!");
									return;
								}


								var url1 = "/AcceptChallenge?quiz_id=" + quiz_id + "&challenger_id=" + challenger_id;


								xhr1.onreadystatechange = handler1;
								xhr1.open("POST", url1, true);
								xhr1.send(null);
							}

							function handler1() {
								if (xhr1.readyState === 4) {
									if (xhr1.status === 200) {
										console.log('accepted');
									} else {
										alert("ERROR accepting challenge");
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
						<% 	User currUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);
							if(currUser.isAdmin()) {
								out.write("<li><a href=\"" + ServletKey.ADMIN_JSP + "\">Admin panel</a></li>");
							}
						%>
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
					out.write(quiz.toHtml(myFriends));

				}
				out.write("</div>");
			%>
			<script>
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

			<p><a href="${pageContext.request.contextPath}/<%= ServletKey.CREATE_QUIZ_JSP%>">Create New Quiz</a></p>
		</div>
	</div>


</div>
</body>
</html>
