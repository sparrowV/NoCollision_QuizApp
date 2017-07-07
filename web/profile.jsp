<%@ page import="database.bean.Quiz" %>
<%@ page import="database.bean.User" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.QuizManager" %>
<%@ page import="model.UserManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="java.util.List" %>
<%@ page import="model.FriendshipManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<%
		// temporary code for filtering
		int userId = Integer.parseInt((String) request.getAttribute("id"));
		User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);

		UserManager userManager = (UserManager) application.getAttribute(ContextKey.USER_MANAGER);
		userManager.getUserById(userId);
		FriendshipManager friendshipManager = (FriendshipManager) application.getAttribute(ContextKey.FRIENDSHIP_MANAGER);
		boolean isFriendAlready = false;
		if (friendshipManager.areFriends(currentUser.getUserId(), userId))
			isFriendAlready = true;
		User user = userManager.getUserById(userId);
		// end of temporary code
		if (user == null)
			user = (User) session.getAttribute(ServletKey.CURRENT_USER);
		System.out.println(user.toString());
		QuizManager manager = (QuizManager) request.getServletContext().getAttribute(ContextKey.QUIZ_MANAGER);
		List<Quiz> quizzes = manager.getQuizzesByAuthorId(user.getUserId());
		String pictureUrl = user.getPicture();
		if (pictureUrl == null || pictureUrl.isEmpty()) {
			pictureUrl = "resources/profile.PNG";
		}
	%>
	<title><%=user.getFirstName() + " " + user.getLastName()%>
	</title>

	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<!-- Custom styles for this web-page -->
	<link rel="stylesheet" type="text/css" href="style.css">

	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	      integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">

	<!-- Bootstrap core JS -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
	        integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
	        crossorigin="anonymous"></script>
</head>
<body>

<div class="container">
	<div class="profile">
		<div class="jumbotron">
			<div class="page-header">
				<h4><%=user.getFirstName() + " " + user.getLastName()%>
				</h4>
				<div class="profile-picture">
					<img src="<%= pictureUrl%>" class="img-thumbnail"
					     alt="<%=user.getFirstName() + " " + user.getLastName()%>">
				</div>
			</div>
			<br>
			<div class="profile-info">
				<p><strong>Username: </strong><%=user.getUsername()%>
				</p>
				<p><strong>Gender: </strong><%=user.getGender()%>
				</p>
				<p><strong>Country: </strong><%=user.getCountry()%>
				</p>
				<p><strong>Date of Birth: </strong><%=user.getDateOfBirth().toString()%>
				</p>
				<% if (currentUser.getUserId() != userId && (!isFriendAlready)) {
					out.println("<button onclick=\"sendRequest(" + userId + ")\">Add Friend</button>\n");
				}%>
				<script>
                    function sendRequest(id) {
                        try {
                            xhr = new XMLHttpRequest();
                        } catch (e) {
                            xhr = new ActiveXObject("Microsoft.XMLHTTP");
                        }
                        if (xhr == null) {
                            alert("Ajax not supported by your browser!");
                            return;
                        }
                        var url = "/FreindRequestResponse?status=2&friend_id=" + id;

                        xhr.onreadystatechange = handler;
                        xhr.open("POST", url, true);
                        xhr.send(null);
                    }


                    function handler() {
                        if (xhr.readyState == 4) {
                            if (xhr.status == 200) {
                                console.log("successful");
                                alert("Friend Request sent")
                            } else {
                                alert("ERROR");
                            }
                        }
                    }
				</script>
			</div>

			<%
				// Create responsive table div.
				out.println("<div class=\"table-responsive\">");

				// Print table header.
				out.println("<table class=\"table\">\n" +
						"  <thead class=\"thead-inverse\">\n" +
						"    <tr>\n" +
						"      <th>ID</th>\n" +
						"      <th>Title</th>\n" +
						"      <th>Date Created</th>\n" +
						"    </tr>\n" +
						"  </thead>\n" +
						"  <tbody>");

				// Print each quiz's html representation.
				for (Quiz quiz : quizzes) {
					out.print(quiz.toHtml());
				}

				// Print table footer.
				out.println("  </tbody>\n" +
						"</table>");

				// Print div closing tag.
				out.println("</div>");
			%>
		</div>
	</div>
</div>
</body>
</html>
