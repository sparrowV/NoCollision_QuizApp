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

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

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
            out.write("<div class=\"item  col-xs-4 col-lg-4\">\n");
            out.write("<div class=\"thumbnail\">\n");
            out.write("<img class=\"group list-group-image\" src='" + currUser.getPicture() + "'" + " alt=\"\"/>\n");
            out.write("<div class=\"caption\">\n");
            out.write("<h4 class=\"group inner list-group-item-heading\">" + currUser.getUsername() + "</h4>\n");
            out.write("<p class=\"group inner list-group-item-text\">" + currUser.getFirstName() + " " + currUser.getLastName() + "</p>\n");

            out.write("<div class=\"row\">\n");

            out.write("<div class=\"col-xs-12 col-md-6\">\n" +
                    "\t<div class=\"w3-container\">\n" +
                    "\n" +
                    "\t\t<button type=\"submit\" id=\"submit_btn\" onclick=\"document.getElementById('id" + currUser.getUserId() + "').style.display='block'\"\n" +
                    "\t\t        class=\"w3-button w3-block w3-light-green\">Message\n" +
                    "\t\t</button>\n" +
                    "\t\t<div id=\"id" + currUser.getUserId() + "\" class=\"w3-modal\">\n" +
                    "\t\t\t<div class=\"w3-modal-content w3-animate-top w3-card-4\">\n" +
                    "\t\t\t\t<header class=\"w3-container w3-light-green\" bor>\n" +
                    "        <span onclick=\"document.getElementById('id" + currUser.getUserId() + "').style.display='none'\"\n" +
                    "              class=\"w3-button w3-display-topright\">&times;</span>\n" +
                    "\t\t\t\t\t<h1>New Message\n" +
                    "\t\t\t\t\t</h1>\n" +
                    "\t\t\t\t</header>\n" +
                    "\t\t\t\t<div class=\"w3-container\">\n" +
                    "\t\t\t\t\t<h2>To: <snap>" + currUser.getUsername() + "</snap></h2>\n" +

                    "<form action=\"/SendMessage\" method=\"post\" onsubmit=\"sendMessage(); return false;\">\n" +
                    "       <input id=\"friend_id\" type=\"hidden\" value=\"" + currUser.getUserId() + "\"/>\n" +
                    "      <input size=\"45\" id=\"message\" type=\"text\"/>\n" +
                    "<br/>\n" +
                    "<br/>\n" +
                    "     <input type=\"submit\" value=\"Send\"/>\n" +
                    "</form>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</div>\n" +
                    "</div>" +


                    "</div>\n" +
                    "</div>\n");
            out.write("</div>\n" +
                    "</div>\n" +
                    "</div>\n");
        }%>

    </div>
</div>

<script>

    function sendMessage() {
        try {
            xhr = new XMLHttpRequest();
        } catch (e) {
            xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        if (xhr === null) {
            alert("Ajax not supported by your browser!");
            return;
        }
        var url = "SendMessage?friend_id=" + document.getElementById("friend_id").value + "&message=" + document.getElementById("message").value;
        console.log(url);
        xhr.onreadystatechange = handler;
        xhr.open("POST", url, true);
        xhr.send(null);

    }

    function handler() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                alert("Message Sent");
                location.reload();
            } else {
                alert("ERROR");
            }
        }
    }
</script>

</body>
</html>
