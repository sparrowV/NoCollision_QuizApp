<%@ page import="database.bean.User" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="model.MessageManager" %>
<%@ page import="servlet.ServletKey" %><%--
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
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script>
		$(document).ready(function () {
			var $textarea = $('#history');
			$textarea.scrollTop($textarea[0].scrollHeight);
		});
	</script>

	<%
		request.setCharacterEncoding("UTF-8");
		String friend_id = request.getParameter("friend_id");
		String friendName = request.getParameter("friend_name");
		MessageManager messageManager = (MessageManager) application.getAttribute(ContextKey.MESSAGE_MANAGER);
		User currentUser = (User) session.getAttribute(ServletKey.CURRENT_USER);

		String chat = messageManager.getChatHistory(currentUser.getUserId(), Integer.parseInt(friend_id), friendName);
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
			<h3><%=friendName%>
			</h3>
		</header>

		<div id="gg" class="w3-container">
			<textarea readonly id="history"><%=chat%></textarea>
		</div>

		<div id="zura" class="w3-container">
			<textarea id="new_message" rows="4" cols="40" style="width:100%"></textarea>
		</div>
		<%
			out.write("\t\t<button onclick=\"sendMessage(" + friend_id + ");\" class=\"w3-button w3-block w3-light-green\">Send Message</button>\n");
		%>
	</div>
</div>


<script>

	window.setInterval(function () {
		/// call your function here
		console.log('log');
		var friend_id = "<%=friend_id%>";
		var friend_name = "<%=friendName%>";
		xhr1 = new XMLHttpRequest();

		var url1 = "/ChatHistory?friend_id=" + friend_id + "&friend_name=" + friend_name;

		//	console.log(url1);
		xhr1.onreadystatechange = handler1;
		xhr1.open("GET", url1, true);
		xhr1.send(null);

	}, 1000);

	function handler1() {
		if (xhr1.readyState === 4) {
			if (xhr1.status === 200) {

				//	console.log(xhr1.responseText);
				document.getElementById('history').value = xhr1.responseText;

				var element = document.getElementById('history');
				//element.focus();
				element.setSelectionRange(element.value.length, element.value.length);


			} else {
				console.log('Server problem...');
			}
		}
	}

	function sendMessage(friend_id) {
		var message = document.getElementById('new_message').value;
		//console.log(message);


		try {
			xhr = new XMLHttpRequest();
		} catch (e) {
			xhr = new ActiveXObject("Microsoft.XMLHTTP");
		}
		if (xhr === null) {
			alert("Ajax not supported by your browser!");
			return;
		}
		var url = "/SendMessage?friend_id=" + friend_id + "&message=" + message;
		//	console.log(url);

		var chat = document.getElementById('history').value;
		chat = chat + message;
		chat = chat + '\n';
		chat = chat + '\n';
		document.getElementById('history').value = chat;
		document.getElementById('new_message').value = '';

		var element = document.getElementById('history');
		element.focus();
		element.setSelectionRange(element.value.length, element.value.length);

		xhr.onreadystatechange = sendMessageHandler();
		xhr.open("POST", url, true);
		xhr.send(null);

	}
	function sendMessageHandler() {
		if (xhr.readyState === 4) {
			if (xhr.status === 200) {

			} else {
				alert('Sending Problem...');
			}
		}
	}

</script>


</body>
</html>
