<%@ page import="model.MessageManager" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="database.bean.User" %><%--
  Created by IntelliJ IDEA.
  User: m1sho
  Date: 13.07.2017
  Time: 1:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
	<title>Mail</title>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">


	<% String friend_id = request.getParameter("friend_id");
		String frinedName = request.getParameter("friend_name");
		MessageManager messageManager = (MessageManager) application.getAttribute(ContextKey.MESSAGE_MANAGER);
		User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);

		String chat = messageManager.getChatHistory(currentUser.getUserId(), Integer.parseInt(friend_id));
		/*System.out.println(friend_id);
		System.out.println("aaa");*/%>

</head>
<body>

<style>
	.w3-container {
		text-align: center;
	}

	#history {
		width: 100%;
		height: 60%;
	}


</style>

<div class="w3-container">
	<h2>Mail</h2>

	<div class="w3-card-4" style="width:100%">
		<header class="w3-container w3-light-grey">
			<h3><%=frinedName%>
			</h3>
		</header>

		<div id="gg" class="w3-container">
			<textarea readonly id="history"><%=chat%></textarea>
		</div>

		<div id="zura" class="w3-container">
			<textarea id="new_message" rows="4" cols="40" style="width:100%"></textarea>
		</div>
		<%
			out.write("\t\t<button onclick=\"send(" + friend_id + ");\" class=\"w3-button w3-block w3-light-green\">Send Message</button>\n");
		%>
	</div>
</div>


<script>
	function send(friend_id) {
		var message = document.getElementById('new_message').value;
		console.log(message);
		location.reload();

		try {
			xhr = new XMLHttpRequest();
		} catch (e) {
			xhr = new ActiveXObject("Microsoft.XMLHTTP");
		}
		if (xhr === null) {
			alert("Ajax not supported by your browser!");
			return;
		}
		var url = "SendMessage?friend_id=" + friend_id + "&message=" + message;

		xhr.onreadystatechange = handler;
		xhr.open("POST", url, true);
		xhr.send(null);

	}
	function handler() {
		if (xhr.readyState === 4) {
			if (xhr.status === 200) {
				//location.reload();
				alert("Sent");
			} else {
				alert("ERROR");
			}
		}
	}

</script>


</body>
</html>
