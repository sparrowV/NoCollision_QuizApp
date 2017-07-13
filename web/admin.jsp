<%@ page import="listener.ContextKey" %>
<%@ page import="model.AnnouncementManager" %>
<%@ page import="servlet.ServletKey" %>
<%@ page import="database.bean.User" %>
<%@ page import="database.bean.Announcement" %><%--
  Created by IntelliJ IDEA.
  User: janxo
  Date: 7/11/2017
  Time: 6:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Admin</title>

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<div class="container">
	<div class="admin">
		<div id="announcement" class="announcement">
			<button id="button-announcement" onclick="createAnnouncement()">Create Announcement</button>
			<script>
				function createAnnouncement() {
					var $textArea = $("<textarea>", {id: "text", class: "announcement-text", rows: 2, cols: 50});
					var $button = $("<button>", {id: "button-addAnnouncement", text: "Add Announcement"});
					$button.click(sendAnnouncement);
					function sendAnnouncement() {
						var text = {text: $textArea.val()};
						$.ajax({
							url: '/AnnouncementServlet',
							type: 'POST',
							data: JSON.stringify(text),
							contentType: 'application/json; charset=utf-8',
							dataType: 'json',
							async: true,
							success: function(msg) {
								alert(msg);
							}
						});
					}

					$("#announcement").append($textArea);
					$("#announcement").append($button);
				}
			</script>
		</div>
	</div>
</div>
</body>
</html>
