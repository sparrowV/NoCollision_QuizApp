<%@ page import="model.UserManager" %>
<%@ page import="listener.ContextKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Title</title>
</head>
<body>
	<%
		int userId = Integer.parseInt(request.getParameter("id"));
		UserManager userManager = (UserManager) application.getAttribute(ContextKey.USER_MANAGER);
		userManager.deleteUser(userId);
		out.print("User Deleted");
	%>
</body>
</html>
