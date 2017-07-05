<%@ page import="database.bean.User" %>
<%@ page import="java.util.List" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="model.FriendshipManager" %>
<%@ page import="listener.ContextKey" %><%--
  Created by IntelliJ IDEA.
  User: m1sho
  Date: 05.07.2017
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<!-- Bootstrap core JS and CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

	<title>My Friends</title>
	<%
		User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);
		FriendshipManager friendshipManager = (FriendshipManager) application.getAttribute(ContextKey.FRIENDSHIP_MANAGER);
		List<User> myFriends = friendshipManager.getFriends(currentUser.getUserId());
		//	System.out.println("~~~~~" + myFriends.size());%>
</head>
<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="javascript:history.back()">Back</a>
		</div>

		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<li><a href="#">My Friends</a></li>
			</ul>
		</div>
	</div>
</nav>

<%--used source https://codepen.io/ajaypatelaj/pen/zIBjJ --%>
<div class="container">
	<div class="well well-sm">
		<div class="btn-group">
			<a href="#" id="list" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-th-list">
            </span>My Friends</a> <a href="#" id="grid" class="btn btn-default btn-sm"><span
				class="glyphicon glyphicon-th"></span>Grid</a>
		</div>
	</div>
	<div id="products" class="row list-group">

		<% for (int i = 0; i < myFriends.size(); i++) {
			User currUser = myFriends.get(i);
			out.write("<div class=\"item  col-xs-4 col-lg-4\">");
			out.write("<div class=\"thumbnail\">");
			out.write("<img class=\"group list-group-image\" src='" + currUser.getPicture() + "'" + " alt=\"\"/>");
			out.write("<div class=\"caption\">");
			out.write("<h4 class=\"group inner list-group-item-heading\">" + currUser.getUsername() + "</h4>");
			out.write("<p class=\"group inner list-group-item-text\">" + currUser.getFirstName() + " " + currUser.getLastName() + "</p>");
			out.write("\t</div>\n" +
					"\t\t\t</div>\n" +
					"\t\t</div>");
		}%>

	</div>
</div>


</body>
</html>
