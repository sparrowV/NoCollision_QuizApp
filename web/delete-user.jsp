<%@ page import="model.UserManager" %>
<%@ page import="listener.ContextKey" %>
<%@ page import="servlet.ServletKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Delte User</title>
</head>
<body>
	<%
		int userId = Integer.parseInt(request.getParameter("id"));
		UserManager userManager = (UserManager) application.getAttribute(ContextKey.USER_MANAGER);
		userManager.deleteUser(userId);
		response.sendRedirect(ServletKey.ADMIN_JSP);
	%>
</body>
</html>
