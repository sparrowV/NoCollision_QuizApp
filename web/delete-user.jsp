<%@ page import="model.UserManager" %>
<%@ page import="listener.ContextKey" %><%--
  Created by IntelliJ IDEA.
  User: janxo
  Date: 7/14/2017
  Time: 6:46 PM
  To change this template use File | Settings | File Templates.
--%>
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
