<%@ page import="database.bean.*" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.*" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
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
%>

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
										"<form action=\"/FriendRequestResponse\"  style=\"text-align: center; margin: auto;\" method=\"post\" onsubmit=\"acceptRequest(1); return false;\">\n" +
										"   <input id=\"accept\" name=\"friend_id\" type=\"hidden\" value=\"" + friendRequests.get(i).getUserId() + "\"/>\n" +
										" <input type=\"submit\"  class=\"btn bg-success\" value=\"Accept\"/>\n" +
										"</form>\n" +
										"<br>\n" +
										"<form action=\"/FriendRequestResponse\" style=\"text-align: center; margin: auto;\" method=\"post\" onsubmit=\"acceptRequest(0); return false;\">\n" +
										"   <input id=\"reject\" name=\"friend_id\" type=\"hidden\" value=\"" + friendRequests.get(i).getUserId() + "\"/>\n" +
										" <input type=\"submit\" class=\"btn bg-warning\" value=\"Reject\"/>\n" +
										"</form>\n" +
										"  </li>\n");
								if (i != friendRequests.size() - 1)
									out.write("<li role='separator' class='divider'></li>\n");
							}%>

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
										" <form  action=\"mail.jsp\" style=\"text-align: center; margin: auto;\" method=\"post\">" +
										"    <input class=\"btn btn-default\" type=\"submit\" value=\"" + myFriends.get(i).getFirstName() + " " + myFriends.get(i).getLastName() + "\"/>\n" +
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
										")\" class=\"btn btn-default\" href=\"do-quiz.jsp?id=" + currChallenge.getChallengedQuizID() + "\">" + currChallenge.getChallengedQuizTitle() + "</a>"
										+ "</li>");
								if (i != myChallenges.size() - 1)
									out.write("<li role='separator' class='divider'></li>\n");
							}
						%>
					</ul>
				</li>

				<form class="navbar-form navbar-left" action="search.jsp" method="post">
					<div class="form-group">
						<input type="text" size="8" class="form-control" placeholder="Search"
						       name="<%= ServletKey.SEARCH%>">>
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
						<% User currUser = (User) request.getSession().getAttribute(ServletKey.CURRENT_USER);
							if (currUser.isAdmin()) {
								out.write("<li><a href=\"/" + ServletKey.ADMIN_JSP + "\">Admin panel</a></li>");
							}
						%>
						<li><a href="javascript:logOut()">Log out</a></li>
					</ul>
				</li>
			</ul>
		</div><!--/.nav-collapse -->
	</div>
</nav>

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


		xhr1.onreadystatechange = acceptedChallengeHandler;
		xhr1.open("POST", url1, true);
		xhr1.send(null);
	}

	function acceptedChallengeHandler() {
		if (xhr1.readyState === 4) {
			if (xhr1.status === 200) {
				console.log('accepted');
			} else {
				alert("ERROR accepting challenge");
			}
		}
	}

	function acceptRequest(control) {
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

		xhr.onreadystatechange = acceptRequestHandler;
		xhr.open("POST", url, true);
		xhr.send(null);
	}


	function acceptRequestHandler() {
		if (xhr.readyState === 4) {
			if (xhr.status === 200) {
				console.log("successful");
				location.reload();
			} else {
				alert("ERROR");
			}
		}
	}

	function logOut() {
		$('<form>', {
			'action': 'Logout',
			'method': 'post'
		}).appendTo(document.body).submit().remove();
	}

</script>